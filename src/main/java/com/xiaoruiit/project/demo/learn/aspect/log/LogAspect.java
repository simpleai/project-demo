package com.xiaoruiit.project.demo.learn.aspect.log;

import com.xiaoruiit.project.demo.utils.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author hanxiaorui
 * @date 2022/12/27
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Around("execution(public * com.xiaoruiit.project.demo.controller..*.*(..))")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        WebLog weblog = new WebLog();

        boolean isFile = false;
        if (proceedingJoinPoint.getArgs() != null){
            for (int i = 0; i < proceedingJoinPoint.getArgs().length; i++) {
                if (proceedingJoinPoint.getArgs()[i] instanceof MultipartFile){// 文件无法序列化
                    isFile = true;
                    break;
                }
            }
        }

        if (!isFile){
            weblog.setRequestParam(proceedingJoinPoint.getArgs());
        }

        weblog.setClassMethodName(proceedingJoinPoint.getTarget().getClass().getName() + "#" + proceedingJoinPoint.getSignature().getName());

        log.info("请求信息:{}", JSON.toJSONString(weblog));

        // 2.执行目标方法
        Object proceed = proceedingJoinPoint.proceed();

        // 目标方法异常，无法记录返回值。先执行切面，后执行@ControllerAdvice
        log.info("返回值:{}",JSON.toJSONString(proceed));

        return proceed;
    }

}
