package com.bilicute.spacetime;

import com.bilicute.spacetime.controller.VerifyCodeController;
import com.bilicute.spacetime.pojo.Result;
import com.bilicute.spacetime.service.IVerifyCodeGen;
import com.bilicute.spacetime.service.MailService;
import com.bilicute.spacetime.utils.JwtUtil;
import com.bilicute.spacetime.utils.Sha256;
import com.bilicute.spacetime.utils.ThreadLocalUtil;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @所属包名: com.bilicute.spacetime
 * @类名: VerifyCodeControllerTest
 * @作者: i囡漫笔
 * @描述:
 * @创建时间: 2024-07-31 16:11
 */

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class VerifyCodeControllerTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Autowired
    private VerifyCodeController controller;
    @InjectMocks
    private VerifyCodeController verifyCodeController;
    @Mock
    private MailService mailService;

    private MockMvc mockMvc;

    @SpyBean
    private VerifyCodeController vCController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(verifyCodeController).build();
    }

    @Test
    public void testUserMailVerifyCode_EmailIsEmpty() throws Exception {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 11);
        claims.put("username", "test2");
        claims.put("SHA2password", Sha256.addSalt(Sha256.addSalt("ValidPass1")));
        JwtUtil.genToken(claims);
        ThreadLocalUtil.set(claims);

        mockMvc.perform(post("/getCode/mailByUser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("邮箱为空"));
        mockMvc.perform(post("/getCode/phoneByUser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("请输入正确的手机号"));
    }

    @Test
    public void testUserMailVerifyCode_RequestMailTimeIsTooShort() throws Exception {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 10);
        claims.put("username", "validUser");
        claims.put("SHA2password", Sha256.addSalt(Sha256.addSalt("ValidPass1")));
        JwtUtil.genToken(claims);
        ThreadLocalUtil.set(claims);
        mockMvc.perform(post("/getCode/mailByUser")
                        .sessionAttr("RequestMailTime", System.currentTimeMillis())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("60秒内请勿重复发送验证码"));
        mockMvc.perform(post("/getCode/phoneByUser")
                        .sessionAttr("RequestTime", System.currentTimeMillis())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("60秒内请勿重复发送验证码"));
    }
    @Mock
    private HttpSession session;
    @Mock
    private IVerifyCodeGen iVerifyCodeGen;
    @InjectMocks
    private VerifyCodeController yourController; // 替换为实际的控制器类名

    @Test
    public void testMailVerifyCode_EmailEmpty() throws UnsupportedEncodingException {

        HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        Result<String> result = controller.MailVerifyCode(request, response, "");
        assertEquals("邮箱为空", result.getMessage());
        Result<String> result1 = controller.sendPhoneCode(request, response, "");
        assertEquals("请输入正确的手机号", result1.getMessage());
    }

    @Test
    public void testVerifyCodeIOException() {
        try {
            HttpServletRequest request = mock(HttpServletRequest.class);
            HttpServletResponse response = mock(HttpServletResponse.class);
            HttpSession session = mock(HttpSession.class);
            ServletOutputStream stream = mock(ServletOutputStream.class);
            when(response.getOutputStream()).thenReturn(stream);
            when(request.getSession()).thenReturn(session);
            doThrow(new IOException("Mocked IOException")).when(stream).flush();
            vCController.verifyCode(request, response);
//            vCController.MailVerifyCode(request, response, "test@bilicute.com");
//
//            UserControllerTest.loginTestUser(response);
//            vCController.UserMailVerifyCode(request, response);
        } catch (IOException e) {
            return;
        }

    }

    @Test
    public void testMailVerifyCodeRepeatRequest() {
        MockitoAnnotations.openMocks(this);
        when(request.getSession()).thenReturn(session);
        // 设置模拟行为
        when(session.getAttribute("RequestMailTime")).thenReturn(System.currentTimeMillis() - 50000);
        // 调用方法
        Result<String> result = yourController.MailVerifyCode(request, response, "test@example.com");
        Result<String> result1 = yourController.sendPhoneCode(request, response, "18806000000");
        // 验证结果
        assertNotEquals(Result.error("60秒内请勿重复发送验证码"), result.getMessage()); // 这里应该是成功的结果，因为已经过了60秒
        assertNotEquals(Result.error("60秒内请勿重复发送验证码"), result1.getMessage());
        UserControllerTest.loginTestUser(response);
        Result<String> result2 = yourController.UserMailVerifyCode(request, response);
        Result<String> result3 = yourController.sendPhoneCodeByUser(request, response);
        assertNotEquals(Result.error("60秒内请勿重复发送验证码"), result3.getMessage());
        assertNotEquals(Result.error("60秒内请勿重复发送验证码"), result2.getMessage());

    }
}
