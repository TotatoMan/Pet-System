package com.bcb.controller;

import com.bcb.config.MqttGateway;
import com.bcb.pojo.EnvirThres;
import com.bcb.pojo.Result;
import com.bcb.service.EnvirThresService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class EnvirThresController {

    @Autowired
    private EnvirThresService envirThresService;
    @Autowired
    private MqttGateway mqttGateway;
 //  @RequestMapping("")
    @GetMapping("/thres")
    public Result threslist()
    {
        log.info("查询环境阈值信息");
        List<EnvirThres> envirThres=envirThresService.threslist();
        return Result.success(envirThres);
    }
    /*@GetMapping("/thres/{id}")
    public  Result thresid(@PathVariable Integer id)
    {
        log.info("根据id查询阈值");
        EnvirThres envirThres=envirThresService.GetbyId(id);
        return Result.success(envirThres);
    }*/
    @PostMapping("/thresupdate")
    public Result thresupdate(@RequestBody EnvirThres envirThres)
    {
        String msg=envirThres.getHumidHigh()+","+envirThres.getHumidLow()+","+envirThres.getTempHigh()+","+envirThres.getTempLow();
        log.info("================生产默认主题的MQTT消息===={}============", msg);
        String topic="/jitBoChuang/mqtt";
        //msg="000d0a";
        mqttGateway.sendToMqtt(topic,msg);
        log.info("修改阈值信息");
        log.info(msg);
        envirThresService.thresupdate(envirThres);
        return Result.success(envirThres);
    }
}
