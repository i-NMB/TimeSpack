package com.bilicute.spacetime;

import com.bilicute.spacetime.controller.VerifyCodeController;
import com.bilicute.spacetime.pojo.Result;
import com.bilicute.spacetime.service.MailService;
import com.bilicute.spacetime.utils.JwtUtil;
import com.bilicute.spacetime.utils.Sha256;
import com.bilicute.spacetime.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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

    @Test
    public void testMailVerifyCode_EmailEmpty() {

        HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        Result<String> result = controller.MailVerifyCode(request, response, "");
        assertEquals("邮箱为空", result.getMessage());
        Result<String> result1 = controller.sendPhoneCode(request, response, "");
        assertEquals("请输入正确的手机号", result1.getMessage());
    }

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
}
