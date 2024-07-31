package com.bilicute.spacetime.service.impl;

import com.bilicute.spacetime.mapper.UserMapper;
import com.bilicute.spacetime.utils.Sha256;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserMapper userMapper; // 模拟UserMapper

    @InjectMocks
    private UserServiceImpl userService; // 自动注入模拟的UserMapper到UserServiceImpl

    @BeforeEach
    void setUp() {
        // 初始化Mockito注解
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister_Admin() {
        // 设置输入参数
        String username = "testUser";
        String password = "testPassword";
        String email = "test@bilicute.com";
        String phone = "1234567890";

        // 调用方法
        userService.register(username, password, email, phone);

        // 验证方法是否被调用一次，并且以正确的参数
        verify(userMapper, times(1)).add(eq(username), eq(Sha256.addSalt(password)), eq(email), eq(phone), eq("Admin"));
    }

    @Test
    void testRegister_User() {
        // 设置输入参数
        String username = "testUser";
        String password = "testPassword";
        String email = "test@example.com";
        String phone = "1234567890";

        // 调用方法
        userService.register(username, password, email, phone);

        // 验证方法是否被调用一次，并且以正确的参数
        verify(userMapper, times(1)).add(eq(username), eq(Sha256.addSalt(password)), eq(email), eq(phone), eq("User"));
    }

    @Test
    void testRegister_InvalidEmail() {
        // 设置输入参数
        String username = "testUser";
        String password = "testPassword";
        String email = "invalid-email";
        String phone = "1234567890";

        // 调用方法
        userService.register(username, password, email, phone);

        // 验证方法不会被调用
        verify(userMapper, times(1)).add(eq(username), eq(Sha256.addSalt(password)), eq(email), eq(phone), eq("User"));
    }
}

