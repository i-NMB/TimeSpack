package com.bilicute.spacetime.annotate;

import com.bilicute.spacetime.validation.IdentityValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @所属包名: com.bilicute.spacetime.annotate
 * @类名: Indetity
 * @作者: i囡漫笔
 * @描述: 自建身份的注释
 * @创建时间间: 2024-04-18 15:33
 */

@Documented    //元注解
@Target({ElementType.PARAMETER, ElementType.FIELD})    //元注解
@Retention(RetentionPolicy.RUNTIME)    //元注解
@Constraint(validatedBy = {IdentityValidation.class})//指定提供校验规则的类


public @interface Identity {
    //提供校验失败后的提示信息
    String message() default "不支持的State类型";
    //指定分组
    Class<?>[] groups() default {};
    //负载 获取到State注解的附加信息
    Class<? extends Payload>[] payload() default {};
}
