package com.bilicute.spacetime.controller;

import com.bilicute.spacetime.pojo.Result;
import com.bilicute.spacetime.pojo.User;
import com.bilicute.spacetime.quickMethods.QuickMethods;
import com.bilicute.spacetime.quickMethods.VerifyCode;
import com.bilicute.spacetime.service.UserService;
import com.bilicute.spacetime.utils.JwtUtil;
import com.bilicute.spacetime.utils.Sha256;
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
import java.util.List;
import java.util.Map;

import static com.bilicute.spacetime.utils.PhoneCodeTool.isPhone;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
@Validated
@Slf4j


public class UserController {

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

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
    public Result<String> register(HttpServletRequest request,
                           @Pattern(regexp ="^\\S{5,16}$" ,message = "请输入5-16位用户名") String username,
                           @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{6,20}$", message = "密码必须要有一个小写字母，一个大写字母和一个数字")
                               @NotNull(message = "密码不能为空")
                               String password,
                           @Pattern(regexp = "^[\\da-zA-Z_-]+@[\\da-zA-Z_-]+\\.(com|cn|email)$",message = "邮箱输入错误或为不支持的邮箱")
                               String email,
                           String phone,
                           String code,
                           String phoneCode,
                           String emailCode) {

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
    public Result<String> login(@Pattern(regexp ="^\\S{5,16}$" , message = "用户名长度不符合规则5-16\n") @NotNull(message = "用户名为空") String username,
                        @Pattern(regexp ="^\\S{5,16}$" , message = "密码长度不符合规则5-16\n") @NotNull(message = "密码为空") String password,
                        @Pattern(regexp ="^\\S{6}$" , message = "图形验证码长度不正确\n") @NotNull(message = "验证码为空") String code,// 添加这一行
                        HttpServletResponse response,
                        HttpServletRequest request){
        // 根据用户名查询用户
        User loginUser=userService.findByUserName(username);
        // 判断该用户是否存在
        if(loginUser==null){
            return Result.error("用户名或密码错误");
        }
        if (!VerifyCode.verifyByImg(request,code)) {
            return Result.error("验证码错误");
        }
        // 判断密码是否正确loginUser对象中的password是密文
        if (Sha256.addSalt(password).equals(loginUser.getPassword()) ){
            // 判断图形验证码code是否正确

            // 登录成功
            // DONE 完成获取JWT的令牌返回到cookie
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",loginUser.getCreateUser());
            claims.put("username",loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
//            response.setHeader("Authorization", token);
            // create a cookie
            Cookie cookie = new Cookie("jwt", token);
             cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
            // cookie.setSecure(true); //安全cookie是仅通过加密的HTTPS连接发送到服务器的cookie。安全Cookie不能通过未加密的HTTP连接传输到服务器。
             cookie.setHttpOnly(true); //当为cookie设置HttpOnly标志时，它会告诉浏览器只有服务器才能访问此特定cookie。
             cookie.setPath("/");
            //add cookie to response
            response.addCookie(cookie);
            request.getSession().invalidate();
            return Result.success(token);
        }
        return Result.error("用户名或密码错误");
    }



    /**
     * @return Result
     * @author i囡漫笔,ytt,王雅妮sinne
     * @description 获取已登陆用户的详细信息
     * @date 2024/4/19
     */
    @GetMapping("/userInfo")
    public Result<?> userInfo(){
        //根据用户名查询用户
        User user = QuickMethods.getLoggedUser();
        if (user == null) {
            return Result.error("查询信息错误");
        }
        return Result.success(user);
    }

    @GetMapping("/getUser")
    public Result<?> getUser(Integer userId){
        //根据用户名查询用户
        User user = userService.findByUserId(userId);
        if (user == null) {
            return Result.error("查询信息错误");
        }
        return Result.success(user);
    }

    /**
     * 关注功能
     * @param passiveId 被关注用户的id
     * @return Result<String>
     */
    @GetMapping("/concern")
    public Result<String> concern(Integer passiveId){
        if(passiveId==null){
            return Result.error("关键数据缺失");
        }
        Integer loggedInUserId = QuickMethods.getLoggedInUserId();
        userService.concern(loggedInUserId,passiveId);
        return Result.success();
    }
    @GetMapping("/disConcern")
    public Result<String> disConcern(Integer passiveId){
        if(passiveId==null){
            return Result.error("关键数据缺失");
        }
        Integer loggedInUserId = QuickMethods.getLoggedInUserId();
        userService.disConcern(loggedInUserId,passiveId);
        return Result.success();
    }
    /**
     * @return Result<?>
     * @author i囡漫笔
     * @description 用户获得已关注用户的列表
     * @date 2024/7/27
     */
    @GetMapping("/getConcern")
    public Result<?> getConcern(){
        Integer loggedInUserId = QuickMethods.getLoggedInUserId();
        List<Integer> getConcern = userService.getConcern(loggedInUserId);
        return Result.success(getConcern);
    }



    /**
     * @param avatarUrl: 头像链接
     * @return Result
     * @author Mjlyb
     * @description 用户上传头像链接后更新头像链接
     * @date 2024/4/19
     */
    @PatchMapping("/updateAvatar")
    public Result<String> updateAvatar(@RequestParam @URL String avatarUrl){
        //TODO 头像上传接口，更新时检测是否为指定域名
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }


    /**
     * @param request: 网络请求（获取Session）
     * @param phoneCode: 手机验证码
     * @param mail: 新邮箱
     * @param mailCode: 新邮箱验证码
     * @return Result
     * @author i囡漫笔
     * @description 已登陆用户修改邮箱验证码
     * @date 2024/4/19
     */
    @PatchMapping("/updateMail")
    public Result<String> updateMail(HttpServletRequest request,String phoneCode,
                             String mail,String mailCode){
        String phone = QuickMethods.getLoggedInPhone();
        if(!VerifyCode.verifyByPhoneInLoggedUser(request,phone,phoneCode)){
            return Result.error("手机验证码错误");
        }
        if (!VerifyCode.verifyByMail(request,mail,mailCode)){
            return Result.error("新邮箱验证码错误");
        }
        userService.updateMail(mail);
        request.getSession().invalidate();
        return Result.success();
    }

    @PatchMapping("/updateNickname")
    public Result<String> updateNickname(String nickname){
        if (nickname == null) {
            return Result.error("输入的昵称为空");
        }
        Integer loggedInUserId = QuickMethods.getLoggedInUserId();
        userService.updateNickname(nickname,loggedInUserId);
        return Result.success();
    }
    @PatchMapping("/changePasswordByMail")
    public Result<String> changePasswordByMail(HttpServletRequest request,
                                       @RequestParam String mailCode,
                                       @RequestParam String newPassword) {
        if (mailCode==null || newPassword==null) {
            return Result.error("关键参数为空");
        }
        String loggedInMail = QuickMethods.getLoggedInEmail();

        if (!VerifyCode.verifyByMailInLoggedUser(request,loggedInMail,mailCode)){
            return Result.error("验证码错误");
        }
        // 更新用户密码
        userService.updatePwd(newPassword);
        // 使邮箱验证码失效
        request.getSession().invalidate();
        return Result.success();
    }

    @PatchMapping("/updatePhone")
    public Result<String> updatePhone(HttpServletRequest request,String mailCode,
                             String phone,String phoneCode){
        String mail = QuickMethods.getLoggedInEmail();
        if(!VerifyCode.verifyByMailInLoggedUser(request,mail,mailCode)){
            return Result.error("邮箱验证码错误");
        }
        if (!isPhone(phone)){
            return Result.error("新手机号码错误");
        }
        if (!VerifyCode.verifyByPhone(request,phone,phoneCode)){
            return Result.error("新手机验证码错误");
        }
        userService.updatePhone(phone);
        request.getSession().invalidate();
        return Result.success();
    }

    @PatchMapping("/changePasswordByPhone")
    public Result<String> changePasswordByPhone(HttpServletRequest request, String phoneCode, String newPassword) {
        String phone = QuickMethods.getLoggedInPhone();
        if (!VerifyCode.verifyByPhoneInLoggedUser(request, phone, phoneCode)) {
            return Result.error("手机验证码错误");
        }
        userService.updatePwd(newPassword);
        request.getSession().invalidate();
        return Result.success();
    }


    @GetMapping("/out")
    public Result<String> deleteAllUserCookies(HttpServletRequest request, HttpServletResponse response) {
        // 获取用户传回的所有cookies
        Cookie cookie = new Cookie("jwt", null);
        cookie.setMaxAge(0); // expires in 7 days
        // cookie.setSecure(true); //安全cookie是仅通过加密的HTTPS连接发送到服务器的cookie。安全Cookie不能通过未加密的HTTP连接传输到服务器。
        cookie.setHttpOnly(true); //当为cookie设置HttpOnly标志时，它会告诉浏览器只有服务器才能访问此特定cookie。
        cookie.setPath("/");
        //add cookie to response
        response.addCookie(cookie);
        request.getSession().invalidate();
        return Result.success();
    }
}
