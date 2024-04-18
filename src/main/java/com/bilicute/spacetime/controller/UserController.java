package com.bilicute.spacetime.controller;

import com.bilicute.spacetime.pojo.Result;
import com.bilicute.spacetime.pojo.User;
import com.bilicute.spacetime.service.UserService;
import com.bilicute.spacetime.utils.JwtUtil;
import com.bilicute.spacetime.utils.Sha256;
import com.bilicute.spacetime.utils.StringUtilsFromTime;
import com.bilicute.spacetime.utils.VerifyCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static com.bilicute.spacetime.utils.PhoneCodeTool.isPhone;
import static com.bilicute.spacetime.utils.StringUtilsFromTime.isEmptyString;

@RestController
@RequestMapping("/user")
@Validated
@Slf4j

public class UserController {
    @Autowired
    private UserService userService;

    /**
     * @param request: 获取的网络请求
     * @param username: 获取的用户名
     * @param password: 获取的密码
     * @param code:  获取的验证码
     * @return Result
     * @author sinnie,iNMB,ytt
     * @description
     * @date 2024/4/17
     */
    @PostMapping("/register")
    public Result register(HttpServletRequest request,
                           @Pattern(regexp ="^\\S{5,16}$" ,message = "请输入5-16位用户名") String username,
                           @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9]{6,20}$", message = "密码必须要有一个小写字母，一个大写字母和一个数字")
                               @NotNull(message = "密码不能为空")
                               String password,
                           @Pattern(regexp = "^[\\da-zA-Z_-]+@[\\da-zA-Z_-]+\\.(com|cn|email)$",message = "邮箱输入错误或为不支持的邮箱")
                               String email,
                           String phone,
                           String code,
                           String phoneCode,
                           String emailCode) {
//        判断参数是否为空
//        String[] stringsArray = new String[]{username, password, email,phone,code,phoneCode,emailCode};
//        if(isEmptyString(stringsArray)){
//            return Result.error("缺少关键参数");
//        }
        if(!isPhone(phone)){
            return Result.error("请输入正确的手机号");
        }
        //验证手机验证码
        if(!VerifyCode.verifyByPhone(request,phone,phoneCode)){
            return Result.error("手机验证码错误");
        }
        //验证验证码
        if (!VerifyCode.verifyByImg(request,code)) {
            return Result.error("图形验证码错误，请刷新图像验证码后重试");
        }
        //验证邮箱是否被修改
        if(!VerifyCode.verifyByMail(request,email,emailCode)){
            return Result.error("邮箱验证码错误");
        }

        //查询用户
        User u = userService.findByUserName(username);
        if (u!=null){
            //占用
            return Result.error("用户名已被占用");
        }
        //没有占用
        //注册
        userService.register(username,password,email,phone);
        return Result.success();

    }

    @PostMapping("/login")
    public Result login(@Pattern(regexp ="^\\S{5,16}$") String username, @Pattern(regexp ="^\\S{5,16}$")String password){
    //根据用户名查询用户
        User loginUser=userService.findByUserName(username);
    //判断该用户是否存在
        if(loginUser==null){
            return Result.error("用户名错误");
        }
        //判断密码是否正确loginguser对象中的password是密文
        if (Sha256.addSalt(password).equals(loginUser.getPassword()) ){
            //登录成功
            return Result.success("jwt token令牌..");
        }
        return Result.error("密码错误");
    }

    @PutMapping("/update")
    public Result update(@RequestBody User user){
        userService.update(user);
        return Result.success();
    }
    @GetMapping("/userInfo")
    public Result<User> userInfo(@RequestHeader(name = "Authorization")String token){
        //根据用户名查询用户
       Map<String,Object> map= JwtUtil.parseToken(token);
        String username=(String) map.get("username");
        User user=userService.findByUserName(username);
        return Result.success(user);
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }


}
