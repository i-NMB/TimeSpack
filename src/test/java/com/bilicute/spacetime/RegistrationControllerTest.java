package com.bilicute.spacetime;


import com.bilicute.spacetime.controller.UserController;
import com.bilicute.spacetime.controller.VerifyCodeController;
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


/**
 * @所属包名: com.bilicute.spacetime
 * @类名: RegistrationControllerTest
 * @作者: i囡漫笔
 * @描述:
 * @创建时间: 2024-07-29 20:50
 */
@SpringBootTest
@Slf4j
public class RegistrationControllerTest {

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

        userController.register(request, username, password, email, phone, code, phoneCode, emailCode);

    }

    @Test
    public void userControllerLogin() {
        String username = "validUser";
        String password = "ValidPass1";
        verifyCodeController.verifyCode(request, response);
        String code = (String) request.getSession().getAttribute("VerifyCode");
        userController.login(username, password, code, response, request);
    }


}

