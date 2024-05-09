package com.bilicute.spacetime.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @所属包名: com.bilicute.spacetime.pojo
 * @类名: ImgUpdate
 * @作者: i囡漫笔
 * @描述: 文章上传时返回的数据类型
 * @创建时间: 2024-05-08 06:55
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ImgUpdate<T> {
    private Integer errno;//业务状态码  0-成功  1-失败
    private String message;//提示信息
    private T data;//响应数据


    //快速返回操作成功响应结果(带响应数据)
    public static <E> ImgUpdate<E> success(E data) {
        return new ImgUpdate<>(0,"操作成功" ,data);
    }
    public static <E> ImgUpdate<E> errorData(E data) {
        return new ImgUpdate<>(1, "操作失败", data);
    }

    //快速返回操作成功响应结果
    public static ImgUpdate success() {
        return new ImgUpdate(0, "操作成功", null);
    }

    public static ImgUpdate error(String message) {
        return new ImgUpdate(1, message, null);
    }

}
