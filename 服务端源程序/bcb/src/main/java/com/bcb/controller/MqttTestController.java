package com.bcb.controller;


import com.bcb.config.MqttGateway;
import com.bcb.pojo.EnvirThres;
import com.bcb.pojo.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * MQTT消息发送
 */
@RestController
@Slf4j
public class MqttTestController {
    /**
     * 注入发送MQTT的Bean
     */
   @Resource
    private MqttGateway mqttGateway;

    /**
     * 发送自定义消息内容（使用默认主题）
     *
     * @param msg 消息内容
     * @return 返回
     */


    @ResponseBody
    @GetMapping(value = "/sendMqtt", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result sendMqtt(@RequestParam String msg) {
        log.info("================生产默认主题的MQTT消息===={}============", msg);
        String topic="/jitBoChuang/mqtt";
        //msg="000d0a";
        mqttGateway.sendToMqtt(topic,msg);
        return Result.success(msg);
    }
    /**
     * 发送自定义消息内容，且指定主题
     *
     * @param msg 消息内容
     * @return 返回
     */
    /*@ResponseBody
    @PostMapping(value = "/sendMqtt2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendMqtt2(@RequestParam("topic") String topic, @RequestParam(value = "msg") String msg) {
        log.info("================生产自定义主题的MQTT消息===={}============", msg);
        mqttGateway.sendToMqtt(topic, msg);
        return new ResponseEntity<>("发送成功", HttpStatus.OK);
    }*/
}