package com.bcb.controller;

import com.bcb.pojo.IdeAnimal;
import com.bcb.pojo.Result;
import com.bcb.service.IdeService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@RestController
public class IdeController {
    @Autowired
    private IdeService ideService;
    @PostMapping("/identify")
    public Result store(@RequestBody IdeAnimal ideAnimal)  {
        log.info("上传识别结果");
        log.info(ideAnimal.toString());
        ideService.store(ideAnimal);
        return Result.success("success");
    }
    @GetMapping("/check")
    public Result ide(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime time){
        log.info("查询识别结果");
        log.info(time.toString());
        IdeAnimal ideAnimal=ideService.check(time);
        return Result.success(ideAnimal);
    }
}
