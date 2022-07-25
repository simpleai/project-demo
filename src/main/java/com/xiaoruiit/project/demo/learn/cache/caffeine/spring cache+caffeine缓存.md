# spring cache+caffeine缓存

## 导包

```java
<dependency>
    <groupId>com.github.ben-manes.caffeine</groupId>
    <artifactId>caffeine</artifactId>
    <version>2.9.0</version>
</dependency>
<dependency>
    <groupId>org.springframework.data</groupId>
    <artifactId>spring-data-redis</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```
## 缓存配置
```java
@EnableCaching
@Configuration
public class CacheConfig {

    @Bean("userCaffeine")
    @Primary
    public CacheManager cacheExclusivelyManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("userCaffeines");
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .initialCapacity(10000)// 初始空间大小
                .expireAfterWrite(120, TimeUnit.SECONDS)// 最后一次写入后120S过期
//                .weakKeys()// key弱引用
                .recordStats());// 统计功能
        return cacheManager;
    }

}
```


## 缓存使用
```java
package com.xiaoruiit.project.demo.learn.cache.caffeine;

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

        return user;
    }

    @CacheEvict(cacheNames = "userCaffeines", cacheManager = "userCaffeine",value="userCaffeines", allEntries=true)
    @Override
    public void clearUserCaffeine(String userCode){
        log.info("清理用户在Caffeine缓存");
        userMap.remove(userCode);
    }
}

```
