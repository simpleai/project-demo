package com.xiaoruiit.project.demo.learn.aspect;

import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RpcLogBean implements ApplicationContextAware {

    public static Map<String, RpcClient> map = new HashMap<>();

    @SneakyThrows
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 从ApplicatContext 中获取所有的@RpcClient注解  返回结果是注入Spring容器的名称和类Class
        Map<String, Object> annotation = applicationContext.getBeansWithAnnotation(RpcClient.class);

        for (Map.Entry<String, Object> entry : annotation.entrySet()) {
            String patch = entry.getKey();

            Class<?> aClass = Class.forName(patch);// 获取对象
            RpcClient rpcClient = aClass.getAnnotation(RpcClient.class);

            Class<?>[] interfaces = aClass.getInterfaces();// 获取类实现的所有接口,即maven依赖的第三方包的接口； Feign使用了maven依赖的方式，那么feign生成的是maven依赖的接口的代理类

            if (interfaces.length == 0){// 未依赖第三方包，feign实现的本接口
                map.put(patch, rpcClient);
            } else {
                for (Class<?> anInterface : interfaces) {// 依赖第三方包，feign实现第三方包的接口
                    map.put(anInterface.getName(), rpcClient);
                }
            }
        }
    }

}
