package com.bilicute.spacetime.validation;

import com.bilicute.spacetime.annotate.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @所属包名: com.bilicute.spacetime.validation
 * @类名: StateValidation
 * @作者: i囡漫笔
 * @描述: 文章已发布草稿状态的继承类
 * @创建时间: 2024-04-25 14:29
 */


public class StateValidation implements ConstraintValidator<State,String>{
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if(value == null){
                return false;
            }
            return value.equals("草稿") || value.equals("已发布");
            //提供校验规则
        }
}
