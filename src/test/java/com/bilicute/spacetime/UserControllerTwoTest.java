package com.bilicute.spacetime;

import com.bilicute.spacetime.controller.UserController;
import com.bilicute.spacetime.pojo.Result;
import com.bilicute.spacetime.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserControllerTwoTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;

    @Test
    public void testRegister() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();


        // 测试数据
        String username = "testUser";
        String password = "Test123";
        String email = "test@example.com";
        String phone = "18806000000";
        String code = "ABCDEF";
        String phoneCode = "123456";
        String emailCode = "123456";

        // 设置session中的属性
        request.getSession().setAttribute("phone", phone);
        request.getSession().setAttribute("PhoneCode", phoneCode);
        request.getSession().setAttribute("Mail", email);
        request.getSession().setAttribute("MailCode", emailCode);
        request.getSession().setAttribute("VerifyCode", code);

        // 调用方法
        Result<String> result = controller.register(request, username, password, email, phone, code, phoneCode, emailCode);
        // 验证结果
        assertEquals("操作成功", result.getMessage());
    }
}