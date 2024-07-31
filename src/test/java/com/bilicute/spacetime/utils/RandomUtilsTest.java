package com.bilicute.spacetime.utils;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class RandomUtilsTest {

    @Test
    void testRandomNumberStringWithValidLength() {
        int length = 10; // 设置期望的字符串长度
        String result = RandomUtils.randomNumberString(length);
        assertNotNull(result); // 确保结果不为null
        assertEquals(length, result.length()); // 确保结果长度正确
        assertTrue(result.matches("\\d+")); // 确保结果只包含数字
    }

    @Test
    void testRandomNumberStringWithZeroLength() {
        int length = 0; // 设置字符串长度为0
        String result = RandomUtils.randomNumberString(length);
        assertNotNull(result); // 确保结果不为null
        assertEquals(length, result.length()); // 确保结果长度为0
    }

    @Test
    void testRandomColorWithValidBounds() {
        int fc = 100;
        int bc = 200;
        Color color = RandomUtils.randomColor(fc, bc);
        assertNotNull(color);
        assertTrue(color.getRed() >= fc && color.getRed() <= bc);
        assertTrue(color.getGreen() >= fc && color.getGreen() <= bc);
        assertTrue(color.getBlue() >= fc && color.getBlue() <= bc);
    }

    @Test
    void testRandomColorWithInvalidBounds() {
        int fc = 300;
        int bc = 100;
        Color color = RandomUtils.randomColor(fc, bc);
        assertNotNull(color);
        assertTrue(color.getRed() >= Math.min(fc, 255) && color.getRed() <= Math.max(bc, fc));
        assertTrue(color.getGreen() >= Math.min(fc, 255) && color.getGreen() <= Math.max(bc, fc));
        assertTrue(color.getBlue() >= Math.min(fc, 255) && color.getBlue() <= Math.max(bc, fc));
    }

}