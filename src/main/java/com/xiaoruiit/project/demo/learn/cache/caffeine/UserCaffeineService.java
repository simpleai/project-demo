package com.xiaoruiit.project.demo.learn.cache.caffeine;

/**
 * @author hanxiaorui
 * @date 2022/7/24
 */
public interface UserCaffeineService {
    User findUserById(String userCode);

    User putUser(User user);

    void clearUserCaffeine(String userCode);
}
