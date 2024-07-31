package com.bilicute.spacetime.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @所属包名: com.bilicute.spacetime.utils
 * @类名: TencentUploadUtilTest
 * @作者: i囡漫笔
 * @描述:
 * @创建时间: 2024-08-01 02:58
 */


public class TencentUploadUtilTest {


    @Test
    public void testGetType_NullFileName() {
        String type = TencentUploadUtil.getType(null);
        assertEquals("", type);
    }

    @Test
    public void testGetType_EmptyFileName() {
        String fileName = "";
        String type = TencentUploadUtil.getType(fileName);
        assertEquals("", type);
    }

    @Test
    public void testGetType_TiffFile() {
        String fileName = "example.tif";
        String type = TencentUploadUtil.getType(fileName);
        assertEquals("image/tiff", type, "Type should be 'image/tiff' for .tif file");
    }

    @Test
    public void testGetType_JpegFile() {
        String fileName = "example.jpeg";
        String type = TencentUploadUtil.getType(fileName);
        assertEquals("image/jpeg", type, "Type should be 'image/jpeg' for .jpeg file");
    }

    @Test
    public void testGetType_GifFile() {
        String fileName = "example.gif";
        String type = TencentUploadUtil.getType(fileName);
        assertEquals("image/gif", type, "Type should be 'image/gif' for .gif file");
    }

    @Test
    public void testGetType_PngFile() {
        String fileName = "example.png";
        String type = TencentUploadUtil.getType(fileName);
        assertEquals("image/png", type, "Type should be 'image/png' for .png file");
    }

    @Test
    public void testGetType_BmpFile() {
        String fileName = "example.bmp";
        String type = TencentUploadUtil.getType(fileName);
        assertEquals("image/bmp", type, "Type should be 'image/bmp' for .bmp file");
    }

    @Test
    public void testGetType_SvgFile() {
        String fileName = "example.svg";
        String type = TencentUploadUtil.getType(fileName);
        assertEquals("image/svg+xml", type, "Type should be 'image/svg+xml' for .svg file");
    }

    @Test
    public void testGetType_WebpFile() {
        String fileName = "example.webp";
        String type = TencentUploadUtil.getType(fileName);
        assertEquals("image/webp", type, "Type should be 'image/webp' for .webp file");
    }

    @Test
    public void testGetType_UnknownFile() {
        String fileName = "example";
        String type = TencentUploadUtil.getType(fileName);
        assertEquals("", type, "Type should be empty string for unknown file type");
    }

    @Test
    public void bytesToHexStringTest() {
        TencentUploadUtil.bytesToHexString(null);
    }
}
