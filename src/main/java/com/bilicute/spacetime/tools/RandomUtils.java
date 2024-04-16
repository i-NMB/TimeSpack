package com.bilicute.spacetime.tools;

import java.awt.*;
import java.util.Random;

/**
 * @项目: everlast
 * @描述: 验证码工具类
 * @作者: i囡漫笔
 * @创建时间: 2023-12-02 09:20
 **/

public class RandomUtils extends org.apache.commons.lang3.RandomUtils {

    private static final char[] CODE_SEQ = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J',
            'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9'};

    private static final char[] NUMBER_ARRAY = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    private static Random random = new Random();

    /**
     * @param length:  随机生成的字符长度
     * @return String
     * @author i囡漫笔
     * @description 随机生成指定字符的字符串
     * @date 2024/4/16 
     */
    public static String randomString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(String.valueOf(CODE_SEQ[random.nextInt(CODE_SEQ.length)]));
        }
        return sb.toString();
    }

    /**
     * @param length:  指定字符的长度
     * @return String
     * @author i囡漫笔
     * @description 生成指定长度的纯数字随机字符串
     * @date 2024/4/16
     */
    public static String randomNumberString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(String.valueOf(NUMBER_ARRAY[random.nextInt(NUMBER_ARRAY.length)]));
        }
        return sb.toString();
    }

    /**
     * @param fc:
     * @param bc:
     * @return Color
     * @author i囡漫笔
     * @description 随机颜色
     * @date 2024/4/16
     */
    public static Color randomColor(int fc, int bc) {
        int f = fc;
        int b = bc;
        Random random = new Random();
        if (f > 255) {
            f = 255;
        }
        if (b > 255) {
            b = 255;
        }
        return new Color(f + random.nextInt(b - f), f + random.nextInt(b - f), f + random.nextInt(b - f));
    }

    public static int nextInt(int bound) {
        return random.nextInt(bound);
    }
}
