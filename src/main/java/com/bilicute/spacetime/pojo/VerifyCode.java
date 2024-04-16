package com.bilicute.spacetime.pojo;

import lombok.Data;

/**
 * @项目: everlast
 * @描述: 验证码类，构建验证码实体
 * @作者: i囡漫笔
 * @创建时间: 2023-12-02 09:10
 **/

@Data
public class VerifyCode {
    private String code;
    private byte[] imgBytes;
    private long expireTime;
}
