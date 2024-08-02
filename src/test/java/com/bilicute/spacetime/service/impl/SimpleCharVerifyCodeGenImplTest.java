package com.bilicute.spacetime.service.impl;

import com.bilicute.spacetime.pojo.VerifyCode;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class SimpleCharVerifyCodeGenImplTest {

    @SpyBean
    private SimpleCharVerifyCodeGenImpl verifyCodeGen;

    @Test
    public void testGenerate_CatchesIOException() throws IOException {
        // 假设generate方法调用了另一个可能抛出IOException的方法
        // 这里我们模拟这个方法抛出IOException
        Mockito.doThrow(new IOException("Mocked IOException")).when(verifyCodeGen).generate(Mockito.anyInt(), Mockito.anyInt(), Mockito.any(ByteArrayOutputStream.class));

        // 调用generate方法
        VerifyCode verifyCode = verifyCodeGen.generate(100, 50);

        // 验证IOException被捕获，并且verifyCode被设置为null
        assertNull(verifyCode, "verifyCode should be null when IOException is thrown");
    }
}
