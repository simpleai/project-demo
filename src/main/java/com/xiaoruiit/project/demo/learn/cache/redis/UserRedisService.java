package com.xiaoruiit.project.demo.learn.cache.redis;

import com.xiaoruiit.project.demo.common.Result;

import java.util.List;

/**
 * @author hanxiaorui
 * @date 2022/7/25
 */
public interface UserRedisService {
    Result initUser();

    List<User> getUserList();
}
