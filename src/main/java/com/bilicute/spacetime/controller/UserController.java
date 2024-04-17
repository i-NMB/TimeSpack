package com.bilicute.spacetime.controller;

import com.bilicute.spacetime.pojo.Result;
import com.bilicute.spacetime.pojo.User;
import com.bilicute.spacetime.service.UserService;
import com.bilicute.spacetime.utils.Md5Util;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public Result register(HttpServletRequest request, @Pattern(regexp ="^\\S{5,16}$") String username, @Validated @RequestBody String password,String code) {

        code = code.toUpperCase();//验证码全转大写
        //验证验证码
        if (!code.equals(request.getSession().getAttribute("VerifyCode"))) {
            request.getSession().setAttribute("VerifyCode",null);
            return Result.error("图形验证码错误，请刷新图像验证码后重试");
        }
//        if (username != null && username.length() >= 5 && username.length() <= 16 &&
//                password != null && password.length() >= 5 && password.length() <= 16)
//        {
        //查询用户
        User u = userService.findByUserName(username);
        if (u!=null){
            //占用
            return Result.error("用户已被占用");
        }
        //没有占用
        //注册
        userService.register(username,password);
        return Result.success();
//    }else{
//        return Result.error("参数不合法"); }
    }
    public Result<String> login(@Pattern(regexp ="^\\S{5,16}$") String username, @Pattern(regexp ="^\\S{5,16}$")String password){
    //根据用户名查询用户
        User loginUser=userService.findByUserName(username );
    //判断该用户是否存在
        if(loginUser==null){
            return Result.error("用户名错误");
        }
        //判断密码是否正确loginguser对象中的password是密文
        if (Md5Util.getMD5String(password).equals(loginUser.getPassword()) ){
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

}
