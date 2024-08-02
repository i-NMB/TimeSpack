package com.bilicute.spacetime.service.impl;

import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @所属包名: com.bilicute.spacetime.service.impl
 * @类名: MailServiceImplTest
 * @作者: i囡漫笔
 * @描述:
 * @创建时间: 2024-08-02 20:44
 */


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MailServiceImplTest {
    @ExtendWith(MockitoExtension.class)
    @Test
    public void testException() {
//        Logger log = Mockito.mock(Logger.class);
        JavaMailSender mockMailSender = Mockito.mock(JavaMailSender.class);
        MimeMessage mimeMessage = Mockito.mock(MimeMessage.class);
        Mockito.when(mockMailSender.createMimeMessage()).thenReturn(mimeMessage);
        Mockito.doThrow(new RuntimeException("Test Exception")).when(mockMailSender).send(Mockito.any(MimeMessage.class));


        MailServiceImpl mailService = new MailServiceImpl();
        mailService.setJavaMailSender(mockMailSender);
        // ... 设置必要的属性 ...
        mailService.sendEmail("test@example.com", "123456", "TestUser");
//        verify(log).error(anyString(), Mockito.any(Throwable.class));
    }

    @Test
    public void testEncodeNicknameWithUnsupportedEncodingException() {
// 1       MimeUtility mockMimeUtility = Mockito.mock(MimeUtility.class);
//        Mockito.when(mockMimeUtility.encodeText(Mockito.anyString())).thenThrow(new UnsupportedEncodingException());
        MailServiceImpl mailService = new MailServiceImpl();
//        mailService.getNickname();
        try (MockedStatic<MimeUtility> mockedStatic = Mockito.mockStatic(MimeUtility.class)) {
            mockedStatic.when(() -> MimeUtility.encodeText("TimeSpace")).thenThrow(new UnsupportedEncodingException());

            String result = mailService.getNickname();

            assertEquals("", result); // Assuming you want to return an empty string on exception
            // Here you would also verify that log.error was called, if possible.
        }

    }
}