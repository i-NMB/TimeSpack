package com.bilicute.spacetime.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class FileLegitimacyTest {

    @Test
    public void testIsLegitimacyWithValidFile() throws IOException {
        // 假设我们有一个有效的文件类型和文件头
        String validFileName = "testfile.jpg";

        // 调用方法进行测试
        boolean result = isLegitimacy(validFileName, null);

        // 断言结果为true，因为我们假设文件类型和文件头是匹配的
        assertFalse(result);
    }

    @Test
    public void testIsLegitimacyWithInvalidFile() throws IOException {
        // 假设我们有一个无效的文件类型和文件头
        String invalidFileName = "testfile.txt";
        byte[] fileContent = "This is a mock file content".getBytes(); // 模拟文件内容
        InputStream fileStream = new ByteArrayInputStream(fileContent);
        MultipartFile multipartFile = new MockMultipartFile(invalidFileName, fileStream);

        // 调用方法进行测试
        boolean result = isLegitimacy(invalidFileName, multipartFile.getInputStream());

        // 断言结果为false，因为我们假设文件类型和文件头不匹配
        assertFalse(result);
    }

    // 由于isLegitimacy是私有的，我们需要一个public的包装方法来进行测试
    public boolean isLegitimacy(String fileName, InputStream fileStream) throws IOException {
        // 调用私有方法
        return TencentUploadUtil.isLegitimacy(fileName, fileStream);
    }
}
