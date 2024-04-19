package com.bilicute.spacetime.controller;

import com.bilicute.spacetime.pojo.Result;
import com.bilicute.spacetime.pojo.User;
import com.bilicute.spacetime.service.UserService;
import com.bilicute.spacetime.utils.JwtUtil;
import com.bilicute.spacetime.utils.Sha256;
import com.bilicute.spacetime.utils.ThreadLocalUtil;
import com.bilicute.spacetime.utils.VerifyCode;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.bilicute.spacetime.utils.PhoneCodeTool.isPhone;

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
    public Result login(@Pattern(regexp ="^\\S{5,16}$") String username,
                        @Pattern(regexp ="^\\S{5,16}$")String password,
                        HttpServletResponse response){
    //根据用户名查询用户
        User loginUser=userService.findByUserName(username);
    //判断该用户是否存在
        if(loginUser==null){
            return Result.error("用户名错误");
        }
        //判断密码是否正确loginUser对象中的password是密文
        if (Sha256.addSalt(password).equals(loginUser.getPassword()) ){
            //登录成功
            //DONE 待完成获取JWT的令牌返回到cookie
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",loginUser.getCreateUser());
            claims.put("username",loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            response.setHeader("Authorization",token);
            return Result.success("登录成功");
        }
        return Result.error("密码错误");
    }

    // FIXME 待修改，笼统的获取User并直接覆盖会使原有的数据丢失以及非法数据侵入
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }
    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader(name = "Authorization")String token*/){
        //根据用户名查询用户
       /*Map<String,Object> map= JwtUtil.parseToken(token);
        String username=(String) map.get("username");*/
        Map<String,Object>map = ThreadLocalUtil.get();
        String username =(String) map.get("username");
        User user=userService.findByUserName(username);
        return Result.success(user);
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

//    @GetMapping("/giveToken")
//    public Result testGiveToken(HttpServletResponse response,HttpServletRequest request){
//        Map<String,Object> claims = new HashMap<>();
//        claims.put("username","iNMB");
//        String token = JwtUtil.genToken(claims);// 创建一个 cookie对象
//        Cookie cookie = new Cookie("jwt", token);
//        cookie.setHttpOnly(true); //不能被js访问的Cookie
//        cookie.setMaxAge(10);
//        //将cookie对象加入response响应
//        response.addCookie(cookie);
//        request.getSession().setAttribute("1",1);
//        return Result.success("成功");
//    }
//
//    @GetMapping("/token")
//    public Result test(@RequestHeader(name = "Authorization")String token){
//        return Result.success(JwtUtil.parseToken(token));
//    }





}
