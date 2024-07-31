package com.bilicute.spacetime;

import com.bilicute.spacetime.controller.AttentionController;
import com.bilicute.spacetime.pojo.Result;
import com.bilicute.spacetime.utils.JwtUtil;
import com.bilicute.spacetime.utils.Sha256;
import com.bilicute.spacetime.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @所属包名: com.bilicute.spacetime
 * @类名: AttentionControllerTest
 * @作者: i囡漫笔
 * @描述: AttentionController
 * @创建时间: 2024-08-01 00:40
 */

@SpringBootTest
public class AttentionControllerTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Autowired
    private AttentionController controller;


    @Test
    public void a() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 10);
        claims.put("username", "validUser");
        claims.put("SHA2password", Sha256.addSalt(Sha256.addSalt("ValidPass1")));
        JwtUtil.genToken(claims);
        ThreadLocalUtil.set(claims);
        HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        Result<String> result = controller.concern(null, request, response);
        assertEquals("关键数据缺失", result.getMessage());
        Result<String> result0 = controller.concern(1, request, response);
        assertEquals("操作成功", result0.getMessage());

        Map<String, Object> claims1 = new HashMap<>();
        claims1.put("id", 10);
        claims1.put("username", "validUser");
        claims1.put("SHA2password", "0");
        JwtUtil.genToken(claims1);
        ThreadLocalUtil.set(claims1);

        Result<String> result1 = controller.concern(1, request, response);
        assertEquals("账号过期，请重新登录", result1.getMessage());
    }

    @Test
    public void b() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 10);
        claims.put("username", "validUser");
        claims.put("SHA2password", Sha256.addSalt(Sha256.addSalt("ValidPass1")));
        JwtUtil.genToken(claims);
        ThreadLocalUtil.set(claims);
        HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        Result<String> result = controller.disConcern(null, request, response);
        assertEquals("关键数据缺失", result.getMessage());
        Result<String> result0 = controller.disConcern(1, request, response);
        assertEquals("操作成功", result0.getMessage());

        Map<String, Object> claims1 = new HashMap<>();
        claims1.put("id", 10);
        claims1.put("username", "validUser");
        claims1.put("SHA2password", "0");
        JwtUtil.genToken(claims1);
        ThreadLocalUtil.set(claims1);

        Result<String> result1 = controller.disConcern(1, request, response);
        assertEquals("账号过期，请重新登录", result1.getMessage());
    }

    @Test
    public void c() {
        UserControllerTest.loginTestUser(response);
        controller.getConcern();
    }

}
