package com.xiaoruiit.project.demo.learn.header.feignaddheader;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 所有的feign调用之前添加调用者信息
 */
@Slf4j
@Configuration
public class FeignHeaderConfiguration implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == attributes) {
            return;
        }

        UserInfo userInfo = UserInfo.current();
        if (userInfo == null) {
            return;
        }

        try {
            template.header("from", "demo");
            template.header("userCode", userInfo.getUserCode());
            template.header("userName", URLEncoder.encode(userInfo.getUserName(), StandardCharsets.UTF_8.toString()));// 乱码

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
