package com.xiaoruiit.project.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

/**
 * @author hanxiaorui
 * @date 2021/12/22
 */
@EnableFeignClients
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@MapperScan({"com.xiaoruiit.project.demo.infrastructure.driver.mapper"}) //添加mapper扫描

public class DemoApplication {
    public static void main(String[] args) {
        // @Value 读取配置
        args = Arrays.copyOf(args, args.length + 1);
        args[args.length - 1] = "--spring.cloud.bootstrap.enabled=true";
        SpringApplication.run(DemoApplication.class,args);

    }
}
