import time
import serial
import pymysql
import struct
import random
import threading
import multiprocessing
from paho.mqtt import client as mqtt_client

# Define global variables to store seven data
Data = [0, 0, 0, 0, 0, 0, 0]    #seven data
order =0x00                     #auto order
mode=0                          #auto or not auto
orders=b'000d0a'                #not auto order
threshold=[60,50,30,20]         #threshold
flag=0

# MQTT_Information
broker = '192.168.1.110'
port = 1883
topic = "/jitBoChuang/mqtt"
client_id = f'refsazfa'

# Port_Information
serial_port = serial.Serial(
    port='/dev/ttyUSB0',
    baudrate=115200,
    bytesize=serial.EIGHTBITS,
    parity=serial.PARITY_NONE,
    stopbits=serial.STOPBITS_ONE,
)
# Wait a second to let the port initialize
time.sleep(1)

# 打开数据库连接
try:
    db = pymysql.connect(host='192.168.1.110', user='root', passwd='123456', port=3306, database='BoChuang')  #text
    
    #db = pymysql.connect(host='192.168.1.109', user='root', passwd='root', port=3306, database='bcb')   #sxq
    print('Connection to the database successfully')
except:
    print('something wrong!')
cursor = db.cursor()


# This function is used to write data to a database
def WriteToDatabase(CO2, CH2O, TVOC, PM25, PM10, Temperature, Humidity):
    sql = """INSERT INTO `BoChuang`.`envir` 
	       (`CO2`, `CH2O`, `TVOC`, `PM25`, `PM10`, `Temperature`, `Humidity`) 
	       VALUES (%s, %s, %s, %s, %s, %s, %s)"""
    values = (CO2, CH2O, TVOC, PM25, PM10, Temperature, Humidity)
    cursor.execute(sql, values)
    db.commit()


# This function is used to convert bytes data into int or float
def Convert_format(datas):
    Data[0] = int(datas[0].decode('utf-8'))
    Data[1] = int(datas[1].decode('utf-8'))
    Data[2] = int(datas[2].decode('utf-8'))
    Data[3] = int(datas[3].decode('utf-8'))
    Data[4] = int(datas[4].decode('utf-8'))
    Data[5] = float(datas[5].decode('utf-8'))
    Data[6] = float(datas[6].decode('utf-8'))
    return Data


# Connect to MQTT
def connect_mqtt() -> mqtt_client:
    def on_connect(client, userdata, flags, rc):
        if rc == 0:
            print("Connected to MQTT Broker successfully")
        else:
            print("Failed to connect, return code %d\n", rc)

    client = mqtt_client.Client(client_id)
    client.on_connect = on_connect
    client.connect(broker, port)
    return client


# Received data from MQTT
def subscribe(client: mqtt_client):

    def on_message(client, userdata, msg):
        print(f"Received `{msg.payload}` from `{msg.topic}` topic")
	
        global threshold
        global mode
        global orders
        global flag
        flag=1	
	
        #not auto mode
        if len(msg.payload)==6:
            print("进入手动模式")
            mode=0
            orders=msg.payload
            #serial_port.write(bytes.fromhex(msg.payload.decode("utf-8")))  # change to hex
        # auto mode
        elif len(msg.payload)==11: 
            mode=1     
            print("进入自动模式")
            threshold=msg.payload.split(b',')
            threshold[0]=float(threshold[0].decode('utf-8'))
            threshold[1]=float(threshold[1].decode('utf-8'))
            threshold[2]=float(threshold[2].decode('utf-8'))
            threshold[3]=float(threshold[3].decode('utf-8'))
            
            
    client.subscribe(topic)
    client.on_message = on_message


# thread2 receiveData
def receiveData():
    #global threshold
    global Data
    try:
        # Send a simple header
        serial_port.write("UART Demonstration Program\r\n".encode())
        serial_port.write("NVIDIA Jetson Nano Developer Kit\r\n".encode())
        while True:
            count = serial_port.inWaiting()
            if count > 0:
                data = serial_port.read(count)  # .decode(encoding='ascii')
                print("receive data is", data)

                # Split the data into individual pieces of data and store them in arrays datas
                datas = data.split(b',')

                # Call the function to convert the format
                Data = Convert_format(datas)

                # Call a function to write data to the database
                WriteToDatabase(Data[0], Data[1], Data[2], Data[3], Data[4], Data[5], Data[6])

    except KeyboardInterrupt:
        print("Exiting Program")
    except Exception as exception_error:
        print("Error occurred. Exiting Program")
        print("Error: " + str(exception_error))
    finally:
        serial_port.close()


# thread1 MQTT
def receiveInstruct():
    #global threshold
    client = connect_mqtt()
    subscribe(client)
    client.loop_forever()
    


if __name__ == '__main__':
    
    # Create two process objects to run two different functions concurrently
    receiveData_thread = threading.Thread(target=receiveData)
    receiveData_thread.start()
    receiveInstruct_thread = threading.Thread(target=receiveInstruct)
    receiveInstruct_thread.start()
    
    while True:
        if flag==1:
            flag=0
            if mode==1:
                time.sleep(0.5)
                order=0x00
                if Data[6]<threshold[0] and Data[6]>threshold[1]:
                    order&=~0x08
                if Data[6]<threshold[1]:
                    order|=0x08
                if Data[5]<threshold[2] and Data[5]>threshold[3]:
                    order&=~0x01
                    order&=~0x02
                if Data[5]>threshold[2]:
                    order|=0x01
                if Data[5]<threshold[3]:
                    order|=0x02

                byte_string=order.to_bytes(1, byteorder='big') + b'\r\n'
                serial_port.write(byte_string)  # change to hex

            elif mode==0:
                serial_port.write(bytes.fromhex(orders.decode("utf-8")))  # change to hex 

    # Wait for the two processes to complete before continuing
    receiveData_thread.join()
    receiveInstruct_thread.join()

    
    # Close the database connection before exiting
    db.close()
