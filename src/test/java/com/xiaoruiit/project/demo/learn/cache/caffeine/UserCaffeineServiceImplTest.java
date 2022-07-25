package com.xiaoruiit.project.demo.learn.cache.caffeine;

import com.xiaoruiit.project.demo.DemoApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = DemoApplication.class)
class UserCaffeineServiceImplTest {

    @Autowired
    UserCaffeineService userService;

    @Test
    void findUserById() {
        User user = userService.findUserById("U1");
        System.out.println("findUserById:" + user);
    }

    @Test
    void putUser() {

        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUserCode("U"+i);
            user.setUserName("张"+i);

            userService.putUser(user);
        }

    }

    @Test
    void clearUserCaffeine() {
        userService.clearUserCaffeine("U1");
    }
}
