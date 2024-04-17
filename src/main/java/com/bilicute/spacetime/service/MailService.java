package com.bilicute.spacetime.service;

/**
 * @项目: everlast
 * @描述: 发送邮箱接口
 * @作者: i囡漫笔
 * @创建时间: 2023-12-02 21:12
 **/

public interface MailService {
    void sendEmail(String email,String code,String userName);
}
