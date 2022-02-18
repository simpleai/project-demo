package com.xiaoruiit.project.demo.learn.valited.annotation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author hanxiaorui
 * @date 2022/2/18
 */
public class MobileValidator implements ConstraintValidator<Mobile, String> {

    private String regexp;

    @Override
    public void initialize(Mobile constraintAnnotation) {
        // 获取手机号的校验格式
        this.regexp = constraintAnnotation.regexp();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(value)) {
            return true;
        }
        return value.matches(regexp);
    }
}
