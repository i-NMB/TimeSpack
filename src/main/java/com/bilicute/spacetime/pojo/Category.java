package com.bilicute.spacetime.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Category {

    @NotNull(message = "id不能为空",groups = {Update.class, Delete.class})
    private Integer categoryId;//主键ID
    @NotEmpty(message = "分类名称为空")
    private String categoryName;//分类名称
    //    @NotEmpty(message = "分类别名为空", groups = {Add.class, Update.class})
    @NotEmpty(message = "分类别名为空")
    private String categoryAlias;//分类别名
    private Integer createUser;//创建人ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间



    /**
     * @author i囡漫笔
     * @description 分组接口,用于把Default添加到Add中，上述没有groups标识的验证符都将继承到Add
     * @date 2024/4/19
     */
    public interface Add extends Default { }

    /**
     * @author i囡漫笔
     * @description 分组接口，用于把Default添加到Update中
     * @date 2024/4/19
     */
    public interface Update extends Default { }

    public interface Delete{ }
}
