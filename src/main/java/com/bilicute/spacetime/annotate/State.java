package com.bilicute.spacetime.annotate;

import com.bilicute.spacetime.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented    //元注解
@Target({ElementType.PARAMETER, ElementType.FIELD})    //元注解
@Retention(RetentionPolicy.RUNTIME)    //元注解
@Constraint(validatedBy = {StateValidation.class})//指定提供校验规则的类
public @interface State {
    String message() default "不支持的文章状态类型";
    //指定分组
    Class<?>[] groups() default {};
    //负载 获取到State注解的附加信息
    Class<? extends Payload>[] payload() default {};
}
