package com.xiaoruiit.project.demo.learn.aspect;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@FeignClient
public @interface RpcClient  {
    @AliasFor("name")
    String value() default "";

    /** @deprecated */
    @Deprecated
    String serviceId() default "";

    String contextId() default "";

    @AliasFor("value")
    String name() default "";

    String qualifier() default "";

    String url() default "";

    boolean decode404() default false;

    Class<?>[] configuration() default {};

    Class<?> fallback() default void.class;

    Class<?> fallbackFactory() default void.class;

    String path() default "";

    boolean primary() default true;

    String serviceName() default "";

    boolean result() default false;

    boolean agrs() default false;

}
