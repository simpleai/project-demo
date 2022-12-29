package com.xiaoruiit.project.demo.learn.cache.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RedisStartupRunner implements CommandLineRunner {

    @Autowired
    private UserRedisService userService;

    @Override
    public void run(String... args) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                userService.initUser();
            }
        }).start();

    }
}
