package com.xiaoruiit.project.demo.learn.aspect;

import com.google.common.base.Throwables;
import com.xiaoruiit.project.demo.common.Result;
import com.xiaoruiit.project.demo.utils.DateUtils;
import com.xiaoruiit.project.demo.utils.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Slf4j
@Configuration
public class RpcAspect {

    /**
     * 项目名称
     */
    @Value("${spring.application.name}")
    public String appName;

    @Bean
    public Advisor rpcLogAdvisor() {

        Pointcut pointcut = new AnnotationMatchingPointcut(RpcClient.class, true);// 定义切面切入点

        RpcLogHandle rpcLogHandle = new RpcLogHandle();
        rpcLogHandle.appName = appName;

        return new DefaultPointcutAdvisor(pointcut, rpcLogHandle);
    }


    public static class RpcLogHandle implements MethodInterceptor {

        public String appName;

        @Override
        public Object invoke(MethodInvocation methodInvocation) throws Throwable {

            Class<?> aClass = methodInvocation.getMethod().getDeclaringClass();// 方法对应的类实例

            RpcClient rpcClient = RpcLogBean.map.get(aClass.getName());

            String serviceName;
            if (rpcClient != null) {
                serviceName = rpcClient.serviceName();// feign注解上的 serviceName
            }else {
                serviceName = methodInvocation.getMethod().getDeclaringClass().getSimpleName();// 类名
            }

            Object proceed = null;
            Throwable e = null;
            Date start = new Date();// 执行方法开始时间
            String startStr = DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss", start);
            Object[] arguments = methodInvocation.getArguments();// 方法请求参数
            String name = methodInvocation.getMethod().getName();// 方法名

            try {
                proceed = methodInvocation.proceed();// 执行代理方法
            } catch (Throwable throwable) {
                e = throwable;
            }

            Date end = new Date();
            String endStr = DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss", end);

            // 构建正常的日志参数
            LogModel logModel = new LogModel();
            logModel.setProjectName(appName);
            logModel.setMethodName(name);
            logModel.setClassName(aClass.getSimpleName());
            logModel.setStartTime(startStr);
            logModel.setEndTime(endStr);
            logModel.setConsumingTime(end.getTime() - start.getTime());
            if (rpcClient != null && rpcClient.agrs()) {
                logModel.setArgs(arguments);
            }
            if (rpcClient != null && rpcClient.result()) {
                logModel.setResult(proceed);
            }

            if (e != null) {// 执行远程调用方法，方法内部报错时
                logModel.setArgs(arguments);
                String msg = "调用" + serviceName + "异常:";
                logModel.setMessage(msg);
                logModel.setException(Throwables.getStackTraceAsString(e));
                throw e;// 有异常，抛出
            }

            if (proceed == null) {// 调用结果是null
                log.warn("服务调用日志:{}", JSON.toJSONString(logModel));
                return null;
            } else if (proceed instanceof Result) {// 返回结果是标准的Result
                Result result = (Result) proceed;
                logModel.setCode(result.getCode());
                if (!result.isOk()) {
                    String msg = "调用" + serviceName + "异常:" + result.getMsg();
                    logModel.setMessage(msg);
                }
                log.info("服务调用日志:{}", JSON.toJSONString(logModel));// feign调用日志
                return proceed;// 无异常，正常返回
            } else {// 返回结果不是标准的Result
                log.error("服务调用日志:{}", JSON.toJSONString(logModel));
                return proceed;
            }
        }
    }

    @Data
    private static class LogModel {

        /**
         * 项目名称
         */
        private String projectName;

        /**
         * 方法名
         */
        private String methodName;

        /**
         * 类名称
         */
        private String className;

        /**
         * 调用方法开始时间
         */
        private String startTime;

        /**
         * 调用方法返回时间
         */
        private String endTime;

        /**
         * 调用方法消耗时间
         */
        private Long consumingTime;

        /**
         * 方法入参
         */
        private Object[] args;

        /**
         * 返回结果
         */
        private Object result;

        /**
         * 远程调用返回code
         */
        private Integer code;

        /**
         * 包装返回消息
         */
        private String message;

        /**
         * 异常代码栈
         */
        private String exception;
    }


}
