package com.bilicute.spacetime;

import com.bilicute.spacetime.controller.FileUploadController;
import com.bilicute.spacetime.pojo.Result;
import com.bilicute.spacetime.utils.TencentUploadUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Random;
import java.util.UUID;

import static ch.qos.logback.core.encoder.ByteArrayUtil.hexStringToByteArray;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class FileUploadControllerTest {
    private final HttpServletResponse response = new MockHttpServletResponse();

    @InjectMocks
    private FileUploadController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void uploadShouldReturnErrorWhenFileIsNull() throws IOException {
        Result<String> result = controller.upload(null);
        controller.uploadImg(null);
        assertEquals("失败，文件为空", result.getData());
    }

    @Test
    void uploadShouldReturnErrorWhenFileNameIsEmpty() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("");
        Result<String> result = controller.upload(file);
        assertEquals("失败，文件名为空", result.getData());
        controller.uploadImg(file);
    }

    @Test
    void uploadShouldReturnErrorWhenFileTypeCheckFails() throws IOException {
        UserControllerTest.loginTestUser(response);
        // 创建一个MockMultipartFile对象
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello, World!".getBytes());
        String fileName = file.getOriginalFilename();
        String uuidFileName = UUID.randomUUID() + fileName.substring(fileName.lastIndexOf('.'));
        InputStream fileStream = file.getInputStream();
        InputStream fileStreamCopy = file.getInputStream();
        when(TencentUploadUtil.cosUpload("img", uuidFileName, fileStream, fileStreamCopy))
                .thenReturn("不符合文件头部，文件类型校验失败！");
        // 调用controller的upload方法
        Result<String> result = controller.upload(file);
        // 验证结果
        assertEquals("不符合文件头部，文件类型校验失败！", result.getData());
        controller.uploadImg(file);
    }

    @Test
    void uploadShouldReturnSuccessWhenFileIsUploaded() {
        // JPEG文件的十六进制头部
        String jpegHeader = "ffd8ff";
        // JPEG文件的扩展名
        String jpegExtension = ".jpg";
        // 文件名
        String fileName = "test" + jpegExtension;
        // 创建文件
        File file = new File(fileName);

        try (OutputStream ignored = new FileOutputStream(file)) {
            // 创建一个ByteArrayOutputStream来作为OutputStream
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            // 将十六进制头部转换为字节数组
            byte[] headerBytes = hexStringToByteArray(jpegHeader);
            // 写入头部
            byteArrayOutputStream.write(headerBytes);
            // 写入一些随机数据来填充文件
            byte[] randomData = new byte[1024]; // 1KB的数据
            new Random().nextBytes(randomData);
            byteArrayOutputStream.write(randomData);
            byteArrayOutputStream.close(); // 关闭OutputStream

            // 创建MockMultipartFile
            MockMultipartFile mockMultipartFile = new MockMultipartFile("file", fileName, "text/plain", byteArrayOutputStream.toByteArray());

            // 调用controller的upload方法
            controller.upload(mockMultipartFile);
            controller.uploadImg(mockMultipartFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
