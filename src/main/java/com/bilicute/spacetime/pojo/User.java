package com.bilicute.spacetime.pojo;


import lombok.Data;
import java.time.LocalDateTime;
@Data
public class User {
    private Integer id;//主键ID
    private String username;//用户名
    private String password;//密码
    private String nickname;//昵称
    private String email;//邮箱
    private String userPic;//用户头像地址
    private String creatTime;//创建时间
    private String updateTime;//更新时间
}
