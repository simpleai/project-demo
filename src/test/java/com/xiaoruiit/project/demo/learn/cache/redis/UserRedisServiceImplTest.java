package com.xiaoruiit.project.demo.learn.cache.redis;

import com.xiaoruiit.project.demo.DemoApplication;
import com.xiaoruiit.project.demo.utils.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = DemoApplication.class)
class UserRedisServiceImplTest {

    @Autowired
    UserRedisService userService;

    @Test
    void getUserList() {
        List<User> userList = userService.getUserList();
        System.out.println("getUserList:" + JSON.toJSONString(userList));
    }


}
