package com.bilicute.spacetime.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data
public class Article {
    private Integer articleId;//主键ID
    @NotEmpty(message = "文章标题为空")
    @Pattern(regexp = "^\\S{1,20}$",message = "文章标题最长为20字符")
    private String title;//文章标题
    @NotEmpty(message = "文章内容为空")
    private String content;//文章内容
    @NotEmpty(message = "封面图像为空")
    @URL(message = "封面图像参数不正确")
    private String coverImg;//封面图像
//    @State
    private String state;//发布状态 已发布|草稿
    @NotNull
    private Integer categoryId;//文章分类id
    private Integer createUser;//创建人ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间

    private Boolean auditingState;
    private Integer likes;
    private Integer view;

}
