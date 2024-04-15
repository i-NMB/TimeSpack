package com.bilicute.spacetime.controller;

import com.bilicute.spacetime.pojo.Result;
import com.bilicute.spacetime.pojo.User;
import com.bilicute.spacetime.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Validated
@Slf4j

public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public Result register(String username, String password){
        //查询用户
        User u = userService.findByUserName(username);
        if (u==null){
            //没有占用
            //注册
            userService.register(username,password);
            return Result.success();
        }else{
            //占用
            return Result.error("用户已被占用");
        }

    }
}
