package com.bilicute.spacetime.pojo;


import lombok.Data;
import java.time.LocalDateTime;
@Data
public class User {
    private Integer create_user;//主键ID
    private String username;//用户名
    private String password;//密码
    private String nickname;//昵称
    private String email;//邮箱
    private String user_pic;//用户头像地址
    private String creat_time;//创建时间
    private String update_time;//更新时间
    private String identity;//身份
}
