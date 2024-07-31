package com.bilicute.spacetime.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @所属包名: com.bilicute.spacetime.utils
 * @类名: StringUtilsFromTimeTest
 * @作者: i囡漫笔
 * @描述: StringUtilsFromTime快速方法测试类
 * @创建时间: 2024-08-01 01:10
 */


public class StringUtilsFromTimeTest {


    // 测试单个字符串的isEmptyString方法
    @Test
    void testIsEmptyStringWithNull() {
        assertTrue(StringUtilsFromTime.isEmptyString((String) null));
    }

    @Test
    void testIsEmptyStringWithEmpty() {
        assertTrue(StringUtilsFromTime.isEmptyString(""));
    }

    @Test
    void testIsEmptyStringWithBlank() {
        assertTrue(StringUtilsFromTime.isEmptyString("   "));
    }

    @Test
    void testIsEmptyStringWithNotEmpty() {
        assertFalse(StringUtilsFromTime.isEmptyString("not empty"));
    }

//    // 测试字符串数组的isEmptyString方法
//    @Test
//    void testIsEmptyStringWithNullArray() {
//        assertTrue(StringUtilsFromTime.isEmptyString((String[]) null));
//    }

    @Test
    void testIsEmptyStringWithEmptyArray() {
        assertFalse(StringUtilsFromTime.isEmptyString(new String[]{}));
    }

    @Test
    void testIsEmptyStringWithArrayContainsNull() {
        assertTrue(StringUtilsFromTime.isEmptyString(new String[]{null, "test"}));
    }

    @Test
    void testIsEmptyStringWithArrayContainsEmpty() {
        assertTrue(StringUtilsFromTime.isEmptyString(new String[]{"", "test"}));
    }

    @Test
    void testIsEmptyStringWithArrayContainsBlank() {
        assertTrue(StringUtilsFromTime.isEmptyString(new String[]{" ", "test"}));
    }

    @Test
    void testIsEmptyStringWithArrayAllNotEmpty() {
        assertFalse(StringUtilsFromTime.isEmptyString(new String[]{"not", "empty"}));
    }
}
