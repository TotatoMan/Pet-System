package com.bcb.controller;

import com.bcb.pojo.Animal;
import com.bcb.pojo.Result;
import com.bcb.service.AnimalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class AnimalController {
    @Autowired
    private AnimalService animalService;
    @GetMapping("/animals")
    public Result animals(@RequestParam String kind)
    {
        log.info("根据种类查询宠物信息");
        Animal animal=animalService.animals(kind);
        return Result.success(animal);
    }

}
