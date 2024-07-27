package com.bilicute.spacetime.pojo;


import com.bilicute.spacetime.annotate.Identity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class User {
    @NotNull(message = "createUser不能为空")
    private Integer createUser;//主键ID

    @Pattern(regexp = "\\S{3,10}(?:'|--|/\\*(?:.|[\\n\\r])*?\\*/|\\b(select|update|and|or|delete|insert|truncate|char|into|substr|ascii|declare|exec|count|master|drop|execute)\\b)"
            , message = "用户名非法")
    private String username;//用户名
    @JsonIgnore//让springmvc把当前对象转换为json字符串的时候，忽略password
    private String password;//密码
    @NotBlank(message = "若使用昵称，昵称不能为空")
    @Pattern(regexp = "\\S{3,10}$"
            , message = "昵称非法")
    private String nickname;//昵称
    @Pattern(regexp = "^[\\da-zA-Z_-]+@[\\da-zA-Z_-]+\\.(com|cn|email)$", message = "邮箱输入错误或为不支持的邮箱")
    private String email;//邮箱
    @URL(message = "头像地址错误")
    private String userPic;//用户头像地址
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updateTime;//更新时间
    @Identity(message = "用户身份不正确")
    private String identity;//身份
    private String phone;//手机号




}