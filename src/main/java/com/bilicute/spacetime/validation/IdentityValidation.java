package com.bilicute.spacetime.validation;

import com.bilicute.spacetime.annotate.Identity;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @所属包名: com.bilicute.spacetime.validation
 * @类名: IdentityValidation
 * @作者: i囡漫笔
 * @描述: 校验Identity身份的继承类
 * @创建时间: 2024-04-18 15:35
 */


public class IdentityValidation implements ConstraintValidator<Identity,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            return false;
        }
        return value.equals("Admin") || value.equals("User") || value.equals("Removed");
        //提供校验规则
    }
}
