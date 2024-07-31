package com.bilicute.spacetime.utils;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtUtilTest {

    // 为了测试，我们可以假设密钥是一个固定的字符串
    private static final String SECRET_KEY = "secret-key-for-testing";

    // 替换getKey方法，以便测试
    public static String getKey() {
        return SECRET_KEY;
    }

    @Test
    void testGenToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", "testUser");
        claims.put("role", "admin");

        String token = JwtUtil.genToken(claims);
        assertNotNull(token);
        // 可以添加更多的断言来验证token的格式和内容
    }

    @Test
    void testParseToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", "testUser");
        claims.put("role", "admin");
        String token = JwtUtil.genToken(claims);

        Map<String, Object> parsedClaims = JwtUtil.parseToken(token);
        assertNotNull(parsedClaims);
        assertEquals(claims.get("user"), parsedClaims.get("user"));
        assertEquals(claims.get("role"), parsedClaims.get("role"));
    }

    @Test
    void testParseTokenWithInvalidToken() {
        String invalidToken = "invalidToken";
        assertThrows(JWTDecodeException.class, () -> JwtUtil.parseToken(invalidToken));
    }

    @Test
    void testParseTokenWithTamperedToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", "testUser");
        claims.put("role", "admin");
        String token = JwtUtil.genToken(claims);

        // 假设我们对token进行了篡改
        String tamperedToken = token + "tampered";
        assertThrows(JWTVerificationException.class, () -> JwtUtil.parseToken(tamperedToken));
    }
}
