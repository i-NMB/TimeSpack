//package com.bilicute.spacetime;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//import com.bilicute.spacetime.controller.VerifyCodeController;
//import com.bilicute.spacetime.pojo.Result;
//import com.bilicute.spacetime.service.MailService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.io.IOException;
//
//public class MailVerifyCodeTest {
//    @Mock
//    private HttpServletRequest request;
//    @Mock
//    private HttpServletResponse response;
//    @Mock
//    private MailService mailService; // 假设你有一个MailService接口
//    @InjectMocks
//    private VerifyCodeController yourController; // 替换YourController为你的实际控制器类名
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//    @ExtendWith(MockitoExtension.class)
//    @Test
//    public void testMailVerifyCode_IOException() {
//        // 设置模拟行为
//        doThrow(new IOException("模拟邮件发送错误")).when(mailService).sendEmail(anyString(), anyString(), anyString());
//        // 调用测试方法
//        Result<String> result = yourController.MailVerifyCode(request, response, "test@example.com");
//        // 验证结果
//        assertEquals("生成邮箱验证码发送错误：模拟邮件发送错误", result.getMessage());
//    }
//}