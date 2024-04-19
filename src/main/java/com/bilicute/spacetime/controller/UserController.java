package com.bilicute.spacetime.controller;

import com.bilicute.spacetime.pojo.Result;
import com.bilicute.spacetime.pojo.User;
import com.bilicute.spacetime.quickMethods.QuickMethods;
import com.bilicute.spacetime.service.UserService;
import com.bilicute.spacetime.utils.JwtUtil;
import com.bilicute.spacetime.utils.Sha256;
import com.bilicute.spacetime.quickMethods.VerifyCode;
import com.bilicute.spacetime.utils.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
     * @param request:  获取的网络请求
     * @param username: 获取的用户名
     * @param password: 获取的密码
     * @param code:     获取的验证码
     * @return Result
     * @author sinnie, iNMB, ytt
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

    /**
     * @param username: 用户名
     * @param password: 密码
     * @param response: 网络响应（添加响应头）
     * @return Result
     * @author i囡漫笔
     * @description 用户登陆并返回用户信息
     * @date 2024/4/19
     */
    @PostMapping("/login")
    public Result login(@Pattern(regexp ="^\\S{5,16}$") String username,
                        @Pattern(regexp ="^\\S{5,16}$")String password,
                        @Pattern(regexp ="^\\S{6}$")String code,// 添加这一行
                        HttpServletResponse response,
                        HttpServletRequest request){
        // 根据用户名查询用户
        User loginUser=userService.findByUserName(username);
        // 判断该用户是否存在
        if(loginUser==null){
            return Result.error("用户名错误");
        }
        if (!VerifyCode.verifyByImg(request,code)) {
            return Result.error("验证码错误");
        }
        // 判断密码是否正确loginUser对象中的password是密文
        if (Sha256.addSalt(password).equals(loginUser.getPassword()) ){
            // 判断图形验证码code是否正确

            // 登录成功
            // DONE 待完成获取JWT的令牌返回到cookie
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",loginUser.getCreateUser());
            claims.put("username",loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            response.setHeader("Authorization", token);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }


    // FIXME 待修改，笼统的获取User并直接覆盖会使原有的数据丢失以及非法数据侵入
    @PutMapping("/update")
    public Result update(@RequestBody User user){
        userService.update(user);
        return Result.success();
    }

    /**
     * @return Result
     * @author i囡漫笔,ytt,王雅妮sinne
     * @description 获取已登陆用户的详细信息
     * @date 2024/4/19
     */
    @GetMapping("/userInfo")
    public Result userInfo(){
        //根据用户名查询用户
        User user = QuickMethods.getLoggedUser();
        if (user == null) {
            return Result.error("查询信息错误");
        }
        return Result.success(user);
    }

    /**
     * @param avatarUrl: 头像链接
     * @return Result
     * @author Mjlyb
     * @description 用户上传头像链接后更新头像链接
     * @date 2024/4/19
     */
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    public Result updatePwd(@RequestBody Map<String, String> params) {

        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");
        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("缺少必要的参数");
        }
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUserName(username);
        if (!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))) {
            return Result.error("原密码填写不正确");
        }
        if (!rePwd.equals(newPwd)) {
            return Result.error("两次填写的新密码不一样");
        }
        userService.updatePwd(newPwd);
        return Result.success();
    }

    /**
     * @param request: 网络请求（获取Session）
     * @param oldMail: 旧邮箱
     * @param oldMailCode: 旧邮箱验证码
     * @return Result
     * @author i囡漫笔
     * @description 已登陆用户修改邮箱验证码
     * @date 2024/4/19
     */
    @PatchMapping("/updateMail")
    public Result updateMail(HttpServletRequest request,String oldMail,String oldMailCode,
                             String newMail,String newMailCode){
        if(!VerifyCode.verifyByMail(request,oldMail,oldMailCode)){
            return Result.error("邮箱验证码错误");
        }


        userService.updateMail(newMail);
        return Result.success();
    }
}
