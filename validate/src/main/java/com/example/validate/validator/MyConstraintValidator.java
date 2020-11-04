package com.example.validate.validator;

import com.example.validate.annotation.MyConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义验证注解 MyConstraint 业务逻辑
 */
public class MyConstraintValidator implements ConstraintValidator<MyConstraint, Object> {
    @Override
    public void initialize(MyConstraint constraintAnnotation) {
        System.out.println("my validator");
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        //true 通过
        //模拟业务逻辑 字符串==222 校验通过
        if ("222".equals(o)) return true;
        return false;
    }
}
