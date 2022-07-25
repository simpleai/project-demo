package com.xiaoruiit.project.demo.learn.cache.caffeine;

import com.xiaoruiit.project.demo.utils.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author hanxiaorui
 * @date 2022/7/24
 */
@Service
@Slf4j
public class UserCaffeineServiceImpl implements UserCaffeineService {

    /**
     * 模拟数据库存储数据
     */
    private static HashMap<String, User> userMap = new HashMap<>();

    @Override
    @Cacheable(key = "'user_' + #userCode",cacheManager = "userCaffeine",cacheNames = "userCaffeines")
    public User findUserById(String userCode) {
        User user = userMap.get(userCode);
        return user;
    }

    @Override
    @CachePut(key = "'user_' + #user.userCode",cacheManager = "userCaffeine",cacheNames = "userCaffeines")
    // 返回值与@Cacheable 返回值一致都是 User 才会更新缓存
    public User putUser(User user) {
        log.info("create user");

        userMap.put(user.getUserCode(), user);

        log.info(JSON.toJSONString(userMap));
        return user;
    }

    @CacheEvict(cacheNames = "userCaffeines", cacheManager = "userCaffeine",value="userCaffeines", allEntries=true)
    @Override
    public void clearUserCaffeine(String userCode){
        log.info("清理用户在Caffeine缓存");
        userMap.remove(userCode);
    }
}
