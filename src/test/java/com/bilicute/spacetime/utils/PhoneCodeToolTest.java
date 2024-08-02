package com.bilicute.spacetime.utils;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PhoneCodeToolTest {

    @Test
    public void testMobileEncryptWithValidMobile() {
        // Arrange
        String validMobile = "13812345678";
        String expected = "13*******78";

        // Act
        String result = PhoneCodeTool.mobileEncrypt(validMobile);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    public void testMobileEncryptWithNull() {
        // Arrange
        String mobile = null;

        // Act
        String result = PhoneCodeTool.mobileEncrypt(mobile);

        // Assert
        assertNull(result);
    }

    @Test
    public void testMobileEncryptWithInvalidLength() {
        // Arrange
        String invalidMobile = "123456789012"; // 12 digits

        // Act
        String result = PhoneCodeTool.mobileEncrypt(invalidMobile);

        // Assert
        assertEquals(invalidMobile, result);
    }

    @Test
    public void testMobileEncryptWithNonNumericCharacters() {
        // Arrange
        String nonNumericMobile = "13812345a78";

        // Act
        String result = PhoneCodeTool.mobileEncrypt(nonNumericMobile);

        // Assert
        assertEquals(nonNumericMobile, result);
    }

    @Test
    public void testMobileEncryptWithEmptyString() {
        // Arrange
        String emptyMobile = "";

        // Act
        String result = PhoneCodeTool.mobileEncrypt(emptyMobile);

        // Assert
        assertEquals(emptyMobile, result);
    }

    @Test
    public void testMobileEncryptWithTenDigits() {
        // Arrange
        String tenDigitMobile = "1381234567";

        // Act
        String result = PhoneCodeTool.mobileEncrypt(tenDigitMobile);

        // Assert
        assertEquals(tenDigitMobile, result);
    }
}
