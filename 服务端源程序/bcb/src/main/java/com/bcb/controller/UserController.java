package com.bcb.controller;

import com.bcb.pojo.Result;
import com.bcb.pojo.User;
import com.bcb.service.UserService;
import com.bcb.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController

public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public Result login(@RequestBody User user)
    {
        log.info("用户登录",user);
        //调用service查询用户名及密码
        User u= userService.login(user);
        //登录成功，生成令牌，下发令牌
        if(u!=null)
        {
            Map<String,Object> claims=new HashMap<>();
            claims.put("id",user.getId());
            claims.put("username",user.getUsername());
            String jwt= JwtUtils.generateJwt(claims);//jwt包含当前登录的信息
            return Result.success(jwt);
        }
        return Result.error("用户名或密码错误");
    }
    @PostMapping("/register")
    public Result register(@RequestBody User user)
    {
        log.info("用户注册");
        //调用service查询是否存在该用户 若存在重新注册 否则新建用户
        User user1=userService.register(user);
        if(user1!=null)
        {
            return Result.error("该用户已存在 请重新输入");
        }
        else
        {
            userService.insert(user);
            return Result.success("注册成功");
        }
    }
    /*@DeleteMapping("/login/{id}")
    public Result delete(@PathVariable Integer id)
    {
        log.info("根据id删除：{}",id);
        userService.delete(id);
        return Result.success();
    }*/
}
