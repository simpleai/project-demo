package com.xiaoruiit.project.demo.learn.header.callerinfo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author hanxiaorui
 * @date 2022/4/9
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public CallerInfoInterceptor requestHeaderContextInterceptor() {
        return new CallerInfoInterceptor();
    }

    /**
     * 绑定拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestHeaderContextInterceptor())
                .addPathPatterns("/**");// 拦截所有请求
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
