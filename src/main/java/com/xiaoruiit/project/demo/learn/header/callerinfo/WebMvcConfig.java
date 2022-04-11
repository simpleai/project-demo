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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestHeaderContextInterceptor())
                .addPathPatterns("/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
