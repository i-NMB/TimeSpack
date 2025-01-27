package com.bilicute.spacetime.service.impl;

import com.bilicute.spacetime.pojo.VerifyCode;
import com.bilicute.spacetime.service.IVerifyCodeGen;
import com.bilicute.spacetime.utils.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;


/**
 * @描述: 验证码实现类
 * @作者: i囡漫笔
 * @创建时间: 2023-12-02 09:17
 **/

@Service
public class SimpleCharVerifyCodeGenImpl implements IVerifyCodeGen {
    private static final Logger logger = LoggerFactory.getLogger(SimpleCharVerifyCodeGenImpl.class);

    private static final String[] FONT_TYPES = {"宋体", "新宋体", "黑体", "楷体", "隶书"};

    private static final int VALICATE_CODE_LENGTH = 6;


    /**
     * @描述: 设置背景颜色及大小，干扰线
     * @param graphics :图形image.getGraphics()
     * @param height : 图像高度
     * @param width : 图像宽度
    */
    private static void fillBackground(Graphics graphics, int width, int height) {
// 填充背景
        graphics.setColor(Color.WHITE);
//设置矩形坐标x y 为0
        graphics.fillRect(0, 0, width, height);

// 加入干扰线条
        for (int i = 0; i < 8; i++) {
//设置随机颜色算法参数
            graphics.setColor(RandomUtils.randomColor(40, 150));
            Random random = new Random();
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            graphics.drawLine(x, y, x1, y1);
        }
    }

    /**
     * @param width: 宽度
     * @param height: 高度
     * @param os: 二进制流
     * @return String
     * @author i囡漫笔
     * @description 生成随机字符
     * @date 2023/12/2
     */
    @Override
    public String generate(int width, int height, OutputStream os) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        fillBackground(graphics, width, height);
        String randomStr = RandomUtils.randomString(VALICATE_CODE_LENGTH);
        createCharacter(graphics, randomStr);
        graphics.dispose();
//设置JPEG格式
        ImageIO.write(image, "JPEG", os);
        return randomStr;
    }

    /**
     * @param width: 验证码宽度
     * @param height: 验证码高度
     * @return VerifyCode
     * @author i囡漫笔
     * @description 验证码生成
     * @date 2023/12/2
     */
    @Override
    public VerifyCode generate(int width, int height) {
        VerifyCode verifyCode;
        try (
//将流的初始化放到这里就不需要手动关闭流
                ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ) {
            String code = generate(width, height, baos);
            verifyCode = new VerifyCode();
            verifyCode.setCode(code);
            verifyCode.setImgBytes(baos.toByteArray());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            verifyCode = null;
        }
        return verifyCode;
    }



    /**
     * @param g: Graphics图像
     * @param randomStr: 随机字符
     * @author i囡漫笔
     * @description 设置字符颜色大小
     * @date 2023/12/2
     */
    private void createCharacter(Graphics g, String randomStr) {
        char[] charArray = randomStr.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
//设置RGB颜色算法参数
            g.setColor(new Color(50 + RandomUtils.nextInt(100),
                    50 + RandomUtils.nextInt(100), 50 + RandomUtils.nextInt(100)));
//设置字体大小，类型
            g.setFont(new Font(FONT_TYPES[RandomUtils.nextInt(FONT_TYPES.length)], Font.BOLD, 26));
//设置x y 坐标
            g.drawString(String.valueOf(charArray[i]), 15 * i + 5, 19 + RandomUtils.nextInt(8));
        }
    }
}
