package com.xiaoruiit.project.demo.learn.cache.redis;

import com.google.common.collect.Maps;
import com.xiaoruiit.project.demo.common.Result;
import com.xiaoruiit.project.demo.utils.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;

/**
 * @author hanxiaorui
 * @date 2022/7/24
 */
@Service
@Slf4j
public class UserRedisServiceImpl implements UserRedisService {

    // 用户key
    private static final String REDIS_KEY_MAP = "user:test_all";

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Result initUser() {
        log.info("初始化用户缓存到redis开始...");
        UserRequest userRequest = new UserRequest();
        List<User> list = this.selectUserList(userRequest);

        Map<String, User> allMap = Maps.newHashMapWithExpectedSize(list.size());

        for (User user : list) {
            allMap.put(user.getUserCode(), user);
        }

        // 缓存所有用户信息
        if (!allMap.isEmpty()) {
            BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(REDIS_KEY_MAP);
            boundHashOperations.expire(30, TimeUnit.MINUTES);
            boundHashOperations.putAll(allMap);
        }

        log.info("初始化用户缓存结束。初始化的用户的数量：{}，具体code列表有：{}", list.size(), list.stream().map(User::getUserCode).collect(toList()));
        return null;
    }

    private List<User> selectUserList(UserRequest userRequest) {
        List<User> users = new ArrayList<>();
        // temp 造数据
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUserCode("U"+i);
            user.setUserName("张"+i);
            users.add(user);
        }
        return users;
    }

    @Override
    public List<User> getUserList() {
        //List<Object> entities = redisTemplate.boundHashOps(REDIS_KEY_MAP).multiGet(branchIds);
        List<Object> entities = redisTemplate.boundHashOps(REDIS_KEY_MAP).values();

        List<User> users = EntityUtils.copy(entities, User.class);

        return users;
    }
}
