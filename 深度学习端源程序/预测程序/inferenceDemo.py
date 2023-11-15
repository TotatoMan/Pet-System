
# -*- coding utf-8 -*-

import cv2
import numpy as np
import requests
# from paddle.inference import Config
# from paddle.inference import PrecisionType
# from paddle.inference import create_predictor
from paddle.fluid.core import AnalysisConfig
from paddle.fluid.core import create_paddle_predictor
import time
import os
import json
# ————————————————图像预处理函数————————————————
def resize_short(img, target_size):
    """ resize_short """
    percent = float(target_size) / min(img.shape[0], img.shape[1])
    resized_width = int(round(img.shape[1] * percent))
    resized_height = int(round(img.shape[0] * percent))
    resized = cv2.resize(img, (resized_width, resized_height))
    return resized

# --------------------裁剪图片-----------------
def crop_image(img, target_size, center):
    """ crop_image """
    height, width = img.shape[:2]
    size = target_size
    if center == True:
        w_start = (width - size) / 2
        h_start = (height - size) / 2
    else:
        w_start = np.random.randint(0, width - size + 1)
        h_start = np.random.randint(0, height - size + 1)
    w_end = w_start + size
    h_end = h_start + size
    img = img[int(h_start):int(h_end), int(w_start):int(w_end), :]
    return img

# -------------------预处理--------------------
def preprocess(img):
    mean = [0.485, 0.456, 0.406]
    std = [0.229, 0.224, 0.225]
    img = resize_short(img, 224)
    img = crop_image(img, 224, True)
    # bgr-> rgb && hwc->chw
    img = img[:, :, ::-1].astype('float32').transpose((2, 0, 1)) / 255
    img_mean = np.array(mean).reshape((3, 1, 1))
    img_std = np.array(std).reshape((3, 1, 1))
    img -= img_mean
    img /= img_std
    return img[np.newaxis, :]

def create_predictor(model_file, params_file):
    # config = AnalysisConfig("")
    config = AnalysisConfig(model_file, params_file)
    config.switch_use_feed_fetch_ops(False)
    config.enable_memory_optim()
    config.enable_use_gpu(1000, 0)
    config.disable_gpu()                                #不使用GPU进行预测
    # 打开TensorRT。此接口的详细介绍请见下文
    config.enable_tensorrt_engine(workspace_size=1 << 30,
                                     max_batch_size=1, min_subgraph_size=1,
                                     precision_mode=AnalysisConfig.Precision.Float32,
                                     use_static=False, use_calib_mode=False)

    predictor = create_paddle_predictor(config)
    return predictor

def run(predictor, img):
    # 预处理图片
    img = preprocess(img)
    input_names = predictor.get_input_names()
    input_tensor = predictor.get_input_tensor(input_names[0])
    input_tensor.reshape(img.shape)
    input_tensor.copy_from_cpu(img.copy())
    # 预测
    predictor.zero_copy_run()
    results = []
    # 获取输出
    output_names = predictor.get_output_names()
    output_tensor = predictor.get_output_tensor(output_names[0])
    output_data = output_tensor.copy_to_cpu()
    # results.append(output_data)
    return output_data
# --------------------展示结果---------------
def post_res(label_dict, res):
    res = res.tolist()
    """
    res：[[0.58512557 0.41412446 0.00074989]]
    res转成list后，是双重liet里面那层是每类的预测结果
    第二重list的个数代表预测的batchsize。这里就只有一张
    """
    res_list=res[0]
    max_probability = max(res_list)
    #print(max_probability)# 打印最大概率
    target_index = res_list.index(max_probability)# 预测概率最大的种类对应的下标
    # if max_probability>=0.75:
    print("结果是:" + "   " + label_dict[target_index]+",预测概率: "+str(max_probability))
    return (max_probability,target_index)
# --------------------上传图片---------------
def post_kind(potoUrl, kind):
    url = "http://192.168.1.112:8088/identify"
    data = {"url": " ", "kind": " "}
    data["url"] = potoUrl
    data["kind"] = kind
    headers = {
        "Content-Type": "application/json",
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6bnVsbCwidXNlcm5hbWUiOiJzeHEiLCJleHAiOjE2ODY1ODE2MzF9.ezCQxaXiQSQtVEBO69QK_yaExsGIRvI5IoMB3-e12g8"}
    response = requests.request("POST", url, data=json.dumps(data), headers=headers)
    print(response.text)


if __name__ == '__main__':
    #label
    label_dict = {0:"cat", 1:"chicken", 2:"dog",3:"hamster",4:"rabbit",5:"squirrel"}
    # 模型文件
    model_file = "./shufflenetv2_v4/__model__"
    params_file = "./shufflenetv2_v4/__params__"
    predictor = create_predictor(model_file, params_file)
    # 调用摄像头
    cap = cv2.VideoCapture(1)
    count=0
    p = 0
    upgit_cmd = '/home/jetbot/Desktop/upgit/upgit ./dic.jpg'
    while True:
        ret,frame = cap.read()
        cv2.imshow("frame", frame)
        print('Predict Start')
        time_start = time.time()
        res = run(predictor,frame)
        print('Time Cost：{}'.format(time.time() - time_start), "s")
        print('Predict End')
        max_probability,target_index = post_res(label_dict,res)
        if max_probability>=0.75:
            count+=1
        if count>=10:
            p=0
            count=0
            cv2.imwrite('./dic.jpg',frame)
            upgit_url = os.popen(upgit_cmd).readlines()
            post_kind(upgit_url[0],label_dict[target_index])
            continue
        else:
            p+=1
        if p>=5:
            count=0
            p=0
            continue
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

