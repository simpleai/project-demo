package com.xiaoruiit.project.demo.learn.header.feignaddheader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hxr
 * @since 2022/4/9
 */
@Slf4j
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {


    /**
     * 每次请求解析token放入对象，并存到当前线程的变量ThreadLocal
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserCode("U123");
        userInfo.setUserName("admin");
        UserInfo.set(userInfo);
        return true;
    }

    /**
     * 请求结束清除线程变量
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserInfo.clean();
    }
}
