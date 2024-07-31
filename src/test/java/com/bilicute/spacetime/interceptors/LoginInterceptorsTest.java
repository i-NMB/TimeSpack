package com.bilicute.spacetime.interceptors;

import com.bilicute.spacetime.utils.JwtUtil;
import com.bilicute.spacetime.utils.Sha256;
import com.bilicute.spacetime.utils.ThreadLocalUtil;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginInterceptorsTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private ThreadLocalUtil threadLocalUtil;

    private LoginInterceptors loginInterceptors;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loginInterceptors = new LoginInterceptors();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    void preHandle_withoutToken() {
        boolean result = loginInterceptors.preHandle(request, response, "");
        loginInterceptors.afterCompletion(request, response, "", null);
        // Then
        assertFalse(result);
        assertEquals(401, response.getStatus());
    }

    @Test
    void preHandle1() {
        // 创建登录拦截器的实例
        LoginInterceptors loginInterceptors = new LoginInterceptors();

        // 创建请求和响应对象
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // 创建claims并生成token
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 10);
        claims.put("username", "validUser");
        claims.put("SHA2password", Sha256.addSalt(Sha256.addSalt("ValidPass1")));
        String token = JwtUtil.genToken(claims);

        // 添加cookie到请求中
        Cookie cookie = new Cookie("jwt", token);
        cookie.setMaxAge(5 * 60);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        request.setCookies(cookie);

        // 调用preHandle方法
        boolean result = loginInterceptors.preHandle(request, response, "");
        loginInterceptors.afterCompletion(request, response, "", null);
        assertTrue(result);
    }

    @Test
    void preHandle2() {
        // 创建登录拦截器的实例
        LoginInterceptors loginInterceptors = new LoginInterceptors();

        // 创建请求和响应对象
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // 创建claims并生成token
        Map<String, Object> claims = new HashMap<>();
        String token = JwtUtil.genToken(claims);

        // 添加cookie到请求中
        Cookie cookie = new Cookie("jwtTest", token);
        cookie.setMaxAge(5 * 60);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        request.setCookies(cookie);

        // 调用preHandle方法
        boolean result = loginInterceptors.preHandle(request, response, "");
        loginInterceptors.afterCompletion(request, response, "", null);
        // 断言结果为false，因为token应该是无效的（这里假设token无效）
        assertFalse(result);
    }
}
