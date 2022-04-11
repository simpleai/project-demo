package com.xiaoruiit.project.demo.learn.header.callerinfo;


import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;


/**
 * @author hanxiaorui
 * @date 2022/4/9
 */
@Slf4j
@Component
public class CallerInfoInterceptor extends HandlerInterceptorAdapter {

    /**
     * 请求被处理前，拦截器拦截，提取调用者信息，放入local
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String userName = URLDecoder.decode(request.getHeader("userName"), StandardCharsets.UTF_8.toString());// URLDecoder解决乱码
            String userCode = request.getHeader("userCode");

            CallerInfo callerInfo = new CallerInfo();
            callerInfo.setUserCode(userCode);
            callerInfo.setUserName(userName);
            CallerInfo.setCallerInfo(callerInfo);// 放入local

            log.debug(CallerInfo.getCallerInfo().toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NullPointerException e){

        }
        return true;
    }

    /**
     * 调用结束后清除线程中调用者信息，防止在线程池中出问题
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
        CallerInfo.removeCallerInfo();
    }
}
