package com.xiaoruiit.project.demo.learn.valited.annotation;

import com.xiaoruiit.project.demo.learn.valited.constant.ValitedContant;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义手机号校验注解
 */
@Documented
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = MobileValidator.class) // 自定义注解需要配置@Constraint(validatedBy = InValidator.class)
public @interface Mobile {

    String message() default ValitedContant.MOBILE_MSG;

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    /**
     * @return an additional regular expression the annotated element must match.
     * 手机号正则表达式
     */
    String regexp() default ValitedContant.MOBILE_REGX;

    /**
     * @return used in combination with {@link #regexp()} in order to specify a regular
     * expression option
     */
    Pattern.Flag[] flags() default { };

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        Mobile[] value();
    }
}
