package com.bilicute.spacetime.pojo;

import lombok.Data;

@Data
public class Category {
    private Integer id;//主键ID
    private String username;//用户名
    private String password;//密码
    private String nickname;//昵称
    private String email;//邮箱
    private String userPic;//用户头像地址
    private String creatTime;//创建时间
    private String updateTime;//更新时间
    private String phone;//手机号
}
