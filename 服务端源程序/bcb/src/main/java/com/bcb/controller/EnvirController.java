package com.bcb.controller;

import com.bcb.pojo.Envir;
import com.bcb.pojo.EnvirThres;
import com.bcb.pojo.PageBean;
import com.bcb.pojo.Result;
import com.bcb.service.EnvirService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
public class EnvirController {
    @Autowired
    private EnvirService envirService;
   /* @RequestMapping("/envirs")
    public Result envirlist()
    {
       log.info("查询所有环境信息");
        List<Envir> envirList=envirService.envirlist();
        return Result.success(envirList);
    }*/
    @GetMapping("/envirs")
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize)
    {
        log.info("分页查询，参数：{},{}",page,pageSize);
        //调用service方法分页查询
        PageBean pageBean=envirService.page(page,pageSize);
        return Result.success(pageBean);
    }
    @GetMapping("/envirscount")
    public Result count(@RequestParam Integer count)
    {
        log.info("根据编号查询环境信息");
        Envir envir=envirService.GetbyCount(count);
        return Result.success(envir);
    }
    @GetMapping("/envirsdate")
    public Result date(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime updateTime){
        log.info("根据时间查询环境信息");
        Envir envir=envirService.GetbyTime(updateTime);
        //log.info(envir.toString());
        if(envir!=null)
        {
            return Result.success(envir);
        }
        else{
            return Result.error("error");
        }
    }
    @GetMapping("/envirsday")
    public Result day(@DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate update){
        log.info("根据日期查询环境信息");
        log.info(update.toString());
        Envir envir=envirService.GetbyDate(update);
        return Result.success(envir);
    }
}
