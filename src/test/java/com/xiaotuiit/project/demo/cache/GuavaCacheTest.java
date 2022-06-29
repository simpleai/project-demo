package com.xiaotuiit.project.demo.cache;

import com.xiaoruiit.project.demo.DemoApplication;
import com.xiaoruiit.project.demo.learn.cache.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author hanxiaorui
 * @date 2022/6/28
 */
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = DemoApplication.class)
public class GuavaCacheTest {
    @Autowired
    UserService userService;

    @Test
    public void testGuavaCache(){
        userService.getUserVoCache("admin");
    }
}
