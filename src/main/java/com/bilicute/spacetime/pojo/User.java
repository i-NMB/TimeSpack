package com.bilicute.spacetime.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Integer createUser;//主键ID
    private String username;//用户名
    @JsonIgnore//让springmvc把当前对象转换为json字符串的时候，忽略password
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9]{6,20}$", message = "密码必须要有一个小写字母，一个大写字母和一个数字")
    private String password;//密码
    private String nickname;//昵称
    private String email;//邮箱
    private String userPic;//用户头像地址
    private String creatTime;//创建时间
    private String updateTime;//更新时间
    private String identity;//身份
    private String phone;//手机号
}