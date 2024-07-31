package com.bilicute.spacetime.exception;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GlobalExceptionHandlerTest {

    @InjectMocks
    GlobalExceptionHandler handler;

    @Test
    public void testHandleException() {
        handler.handleException(new RuntimeException());
    }
}
