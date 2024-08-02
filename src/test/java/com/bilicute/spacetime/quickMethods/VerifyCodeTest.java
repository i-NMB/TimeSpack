package com.bilicute.spacetime.quickMethods;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VerifyCodeTest {

    @Test
    public void testVerifyByPhoneWithValidCode() {
        // 模拟HttpServletRequest对象
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        // 模拟HttpSession对象
        HttpSession session = Mockito.mock(HttpSession.class);
        // 设置模拟的session属性
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("phone")).thenReturn("1234567890");
        Mockito.when(session.getAttribute("PhoneCode")).thenReturn("123456");

        // 调用verifyByPhone方法进行测试
        boolean result = VerifyCode.verifyByPhone(request, "1234567890", "123456");

        // 断言结果为true，因为手机号和验证码都匹配
        assertTrue(result);
    }

    @Test
    public void testVerifyByPhoneWithInvalidPhone() {
        // 模拟HttpServletRequest对象
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        // 模拟HttpSession对象
        HttpSession session = Mockito.mock(HttpSession.class);
        // 设置模拟的session属性
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("phone")).thenReturn("0987654321");

        // 调用verifyByPhone方法进行测试
        boolean result = VerifyCode.verifyByPhone(request, "1234567890", "123456");

        // 断言结果为false，因为手机号不匹配
        assertFalse(result);
    }

    @Test
    public void testVerifyByPhoneWithInvalidCode() {
        // 模拟HttpServletRequest对象
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        // 模拟HttpSession对象
        HttpSession session = Mockito.mock(HttpSession.class);
        // 设置模拟的session属性
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("phone")).thenReturn("1234567890");
        Mockito.when(session.getAttribute("PhoneCode")).thenReturn("654321");

        // 调用verifyByPhone方法进行测试
        boolean result = VerifyCode.verifyByPhone(request, "1234567890", "123456");

        // 断言结果为false，因为验证码不匹配
        assertFalse(result);
    }

    @Test
    public void testVerifyByMailWithValidCode() {
        // 模拟HttpServletRequest对象
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        // 模拟HttpSession对象
        HttpSession session = Mockito.mock(HttpSession.class);
        // 设置模拟的session属性
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("Mail")).thenReturn("example@example.com");
        Mockito.when(session.getAttribute("MailCode")).thenReturn("ABC123");

        // 调用verifyByMail方法进行测试
        boolean result = VerifyCode.verifyByMail(request, "example@example.com", "abc123");

        // 断言结果为true，因为邮箱地址和验证码都匹配
        assertTrue(result);
    }

    @Test
    public void testVerifyByMailWithInvalidEmail() {
        // 模拟HttpServletRequest对象
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        // 模拟HttpSession对象
        HttpSession session = Mockito.mock(HttpSession.class);
        // 设置模拟的session属性
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("Mail")).thenReturn("different@example.com");

        // 调用verifyByMail方法进行测试
        boolean result = VerifyCode.verifyByMail(request, "example@example.com", "abc123");

        // 断言结果为false，因为邮箱地址不匹配
        assertFalse(result);
    }

    @Test
    public void testVerifyByMailWithInvalidCode() {
        // 模拟HttpServletRequest对象
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        // 模拟HttpSession对象
        HttpSession session = Mockito.mock(HttpSession.class);
        // 设置模拟的session属性
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("Mail")).thenReturn("example@example.com");
        Mockito.when(session.getAttribute("MailCode")).thenReturn("XYZ789");

        // 调用verifyByMail方法进行测试
        boolean result = VerifyCode.verifyByMail(request, "example@example.com", "abc123");

        // 断言结果为false，因为验证码不匹配
        assertFalse(result);
    }

    @Test
    public void testVerifyByMailWithNullCode() {
        // 模拟HttpServletRequest对象
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        // 模拟HttpSession对象
        HttpSession session = Mockito.mock(HttpSession.class);
        // 设置模拟的session属性
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("Mail")).thenReturn("example@example.com");

        // 调用verifyByMail方法进行测试
        boolean result = VerifyCode.verifyByMail(request, "example@example.com", null);

        // 断言结果为false，因为验证码为null
        assertFalse(result);
    }

    @Test
    public void testVerifyByPhoneInLoggedUserWithValidCode() {
        // 模拟HttpServletRequest对象
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        // 模拟HttpSession对象
        HttpSession session = Mockito.mock(HttpSession.class);
        // 设置模拟的session属性
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("loggedInPhone")).thenReturn("1234567890");
        Mockito.when(session.getAttribute("PhoneCode")).thenReturn("123456");

        // 调用verifyByPhoneInLoggedUser方法进行测试
        boolean result = VerifyCode.verifyByPhoneInLoggedUser(request, "1234567890", "123456");

        // 断言结果为true，因为手机号和验证码都匹配
        assertTrue(result);
    }

    @Test
    public void testVerifyByPhoneInLoggedUserWithInvalidPhone() {
        // 模拟HttpServletRequest对象
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        // 模拟HttpSession对象
        HttpSession session = Mockito.mock(HttpSession.class);
        // 设置模拟的session属性
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("loggedInPhone")).thenReturn("0987654321");

        // 调用verifyByPhoneInLoggedUser方法进行测试
        boolean result = VerifyCode.verifyByPhoneInLoggedUser(request, "1234567890", "123456");

        // 断言结果为false，因为手机号不匹配
        assertFalse(result);
    }

    @Test
    public void testVerifyByPhoneInLoggedUserWithInvalidCode() {
        // 模拟HttpServletRequest对象
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        // 模拟HttpSession对象
        HttpSession session = Mockito.mock(HttpSession.class);
        // 设置模拟的session属性
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("loggedInPhone")).thenReturn("1234567890");
        Mockito.when(session.getAttribute("PhoneCode")).thenReturn("654321");

        // 调用verifyByPhoneInLoggedUser方法进行测试
        boolean result = VerifyCode.verifyByPhoneInLoggedUser(request, "1234567890", "123456");

        // 断言结果为false，因为验证码不匹配
        assertFalse(result);
    }

    @Test
    public void testVerifyByPhoneInLoggedUserWithNullCode() {
        // 模拟HttpServletRequest对象
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        // 模拟HttpSession对象
        HttpSession session = Mockito.mock(HttpSession.class);
        // 设置模拟的session属性
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("loggedInPhone")).thenReturn("1234567890");

        // 调用verifyByPhoneInLoggedUser方法进行测试
        boolean result = VerifyCode.verifyByPhoneInLoggedUser(request, "1234567890", null);

        // 断言结果为false，因为验证码为null
        assertFalse(result);
    }
}
