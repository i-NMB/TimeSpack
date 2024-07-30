package com.bilicute.spacetime;


import com.bilicute.spacetime.controller.UserController;
import com.bilicute.spacetime.controller.VerifyCodeController;
import com.bilicute.spacetime.utils.JwtUtil;
import com.bilicute.spacetime.utils.Sha256;
import com.bilicute.spacetime.utils.ThreadLocalUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.HashMap;
import java.util.Map;


/**
 * @所属包名: com.bilicute.spacetime
 * @类名: UserControllerTest
 * @作者: i囡漫笔
 * @描述:
 * @创建时间: 2024-07-29 20:50
 */
@SpringBootTest
@Slf4j
public class UserControllerTest {

    private final HttpServletRequest request = new MockHttpServletRequest();
    private final HttpServletResponse response = new MockHttpServletResponse();
    @Autowired
    private UserController userController;
    @Autowired
    private VerifyCodeController verifyCodeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * @author i囡漫笔
     * @description 测试UserController中的注册方法
     * @date 2024/7/29
     */

    @Test
    public void userControllerRegister() {

        String username = "validUser";
        String password = "ValidPass1";
        String email = "test@example.com";
        String phone = "18806080702";

        verifyCodeController.verifyCode(request, response);
        verifyCodeController.MailVerifyCode(request, response, email);
        verifyCodeController.sendPhoneCode(request, response, phone);

        String code = (String) request.getSession().getAttribute("VerifyCode");
        String phoneCode = (String) request.getSession().getAttribute("PhoneCode");
        String emailCode = (String) request.getSession().getAttribute("MailCode");
        //手机号错误
        userController.register(request, username, password, email, "0", code, phoneCode, emailCode);
        //手机验证码错误
        userController.register(request, username, password, email, phone, code, "000000", emailCode);
        //图形验证码错误
        userController.register(request, username, password, email, phone, "000000", phoneCode, emailCode);
        //邮箱验证码错误
        userController.register(request, username, password, email, phone, code, phoneCode, "000000");
        //正确注册
        userController.register(request, username, password, email, phone, code, phoneCode, emailCode);
    }

    /**
     * @author i囡漫笔
     * @description 测试UserController中的登录方法
     * @date 2024/7/29
     */
    @Test
    public void userControllerLogin() {
        String username = "validUser";
        String password = "ValidPass1";
        verifyCodeController.verifyCode(request, response);
        String code = (String) request.getSession().getAttribute("VerifyCode");
        //用户名为空
        userController.login("testNeed", password, code, response, request);
        //验证码错误
        userController.login(username, password, "000000", response, request);
        //密码错误
        userController.login(username, "password", code, response, request);
        //正确登录
        userController.login(username, password, code, response, request);
    }

    public static void loginTestUser(HttpServletResponse response) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 10);
        claims.put("username", "validUser");
        claims.put("SHA2password", Sha256.addSalt(Sha256.addSalt("ValidPass1")));
        String token = JwtUtil.genToken(claims);
        // 假设ThreadLocalUtil.set()是用来设置ThreadLocal的值
        ThreadLocalUtil.set(claims);
        Cookie cookie = new Cookie("jwt", token);
        cookie.setMaxAge(5 * 60);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Test
    public void userControllerGetUser() {
        userController.getUser(null);
        userController.getUser(2);
    }

    @Test
    public void userControllerUserInfo() {
        //模拟登录
        UserControllerTest.loginTestUser(response);
        userController.userInfo(request, response);
        //验证修改密码后“账号过期”
        Map<String, Object> claims2 = new HashMap<>();
        claims2.put("id", 10);
        claims2.put("username", "validUser");
        claims2.put("SHA2password", "");
        ThreadLocalUtil.set(claims2);
        userController.userInfo(request, response);
    }

    @Test
    public void userControllerUpdateAvatar() {
        //模拟登录
        UserControllerTest.loginTestUser(response);

        userController.updateAvatar("https://everlast-1310624778.cos.ap-chengdu.myqcloud.com/img/fe4ceda8-33e5-4634-98a2-a3d0b9a6dca1.png",
                request, response);
        Map<String, Object> claims2 = new HashMap<>();
        claims2.put("id", 10);
        claims2.put("username", "validUser");
        claims2.put("SHA2password", "0");
        ThreadLocalUtil.set(claims2);
        userController.updateAvatar("https://everlast-1310624778.cos.ap-chengdu.myqcloud.com/img/fe4ceda8-33e5-4634-98a2-a3d0b9a6dca1.png",
                request, response);
    }

    @Test
    public void userControllerUpdateMail() {
        //模拟登录
        UserControllerTest.loginTestUser(response);

        verifyCodeController.sendPhoneCodeByUser(request, response);
        String phoneCode = (String) request.getSession().getAttribute("PhoneCode");
        String email = "test@example.com";
        verifyCodeController.MailVerifyCode(request, response, email);
        verifyCodeController.MailVerifyCode(request, response, email);
        String mailCode = (String) request.getSession().getAttribute("MailCode");
        //手机验证码错误测试
        userController.updateMail(request, response, "000000", email, mailCode);
        //邮箱验证码错误测试
        userController.updateMail(request, response, phoneCode, email, "000000");
        //正确运行
        userController.updateMail(request, response, phoneCode, email, mailCode);

        //修改密码敏感操作测试
        Map<String, Object> claims2 = new HashMap<>();
        claims2.put("id", 10);
        claims2.put("username", "validUser");
        claims2.put("SHA2password", "0");
        ThreadLocalUtil.set(claims2);
        userController.updateMail(request, response, phoneCode, email, mailCode);
    }

    @Test
    public void userControllerUpdateNickname() {
        //模拟登录
        UserControllerTest.loginTestUser(response);
        //nickname为空
        userController.updateNickname(null, request, response);
        //正确运行
        userController.updateNickname("validUser", request, response);

        //修改密码敏感操作测试
        Map<String, Object> claims2 = new HashMap<>();
        claims2.put("id", 10);
        claims2.put("username", "validUser");
        claims2.put("SHA2password", "0");
        ThreadLocalUtil.set(claims2);
        //修改密码后敏感操作错误测试
        userController.updateNickname("validUser", request, response);
    }

    @Test
    public void userControllerChangePasswordByMail() {
        //模拟登录
        UserControllerTest.loginTestUser(response);

        verifyCodeController.UserMailVerifyCode(request, response);
        String mailCode = (String) request.getSession().getAttribute("MailCode");
        String newPassword = "ValidPass1";
        //邮箱验证码为空测试
        userController.changePasswordByMail(request, response, null, newPassword);
        //新密码为空测试
        userController.changePasswordByMail(request, response, mailCode, null);
        //验证码错误测试
        userController.changePasswordByMail(request, response, "000000", newPassword);
        //正确运行
        userController.changePasswordByMail(request, response, mailCode, newPassword);

        //修改密码敏感操作测试
        Map<String, Object> claims2 = new HashMap<>();
        claims2.put("id", 10);
        claims2.put("username", "validUser");
        claims2.put("SHA2password", "0");
        ThreadLocalUtil.set(claims2);
        //修改密码后敏感操作错误测试
        userController.changePasswordByMail(request, response, mailCode, newPassword);

    }

    @Test
    public void userControllerUpdatePhone() {
        //模拟登录
        UserControllerTest.loginTestUser(response);
        verifyCodeController.UserMailVerifyCode(request, response);
        verifyCodeController.UserMailVerifyCode(request, response);//验证60s内重复发验证码
        String mailCode = (String) request.getSession().getAttribute("MailCode");
        String phone = "18806080702";
        verifyCodeController.sendPhoneCode(request, response, phone);
        verifyCodeController.sendPhoneCode(request, response, phone);
        String phoneCode = (String) request.getSession().getAttribute("PhoneCode");

        //邮箱验证码为空
        userController.updatePhone(request, response, null, phone, phoneCode);
        //手机号为空
        userController.updatePhone(request, response, mailCode, null, phoneCode);
        //短信验证码为空
        userController.updatePhone(request, response, mailCode, phone, null);

        //正确运行
        userController.updatePhone(request, response, mailCode, phone, phoneCode);

        //修改密码敏感操作测试
        Map<String, Object> claims2 = new HashMap<>();
        claims2.put("id", 10);
        claims2.put("username", "validUser");
        claims2.put("SHA2password", "0");
        ThreadLocalUtil.set(claims2);
        userController.updatePhone(request, response, mailCode, phone, phoneCode);
    }

    @Test
    public void userControllerChangePasswordByPhone() {
        //模拟登录
        UserControllerTest.loginTestUser(response);

        verifyCodeController.sendPhoneCodeByUser(request, response);
        String phoneCode = (String) request.getSession().getAttribute("PhoneCode");
        String newPassword = "ValidPass1";

        //手机验证码错误
        userController.changePasswordByPhone(request, response, null, newPassword);
        //新密码错误
        userController.changePasswordByPhone(request, response, phoneCode, null);
        //正确运行
        userController.changePasswordByPhone(request, response, phoneCode, newPassword);

        //修改密码敏感操作测试
        Map<String, Object> claims2 = new HashMap<>();
        claims2.put("id", 10);
        claims2.put("username", "validUser");
        claims2.put("SHA2password", "0");
        ThreadLocalUtil.set(claims2);
        //修改密码后敏感操作错误测试
        userController.changePasswordByPhone(request, response, phoneCode, newPassword);

        userController.deleteAllUserCookies(request, response);
    }

}

