package com.xiaoruiit.project.demo.learn.cache.caffeine;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

/**
 * @author hanxiaorui
 * @date 2022/7/8
 *
 * 缓存配置
 */
@EnableCaching
@Configuration
public class CacheConfig {

    @Bean("userCaffeine")
    @Primary
    public CacheManager cacheExclusivelyManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("userCaffeines");
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .initialCapacity(1)// 初始空间大小
                .maximumSize(1) // ? 实测无效果
                .expireAfterWrite(120, TimeUnit.SECONDS)// 最后一次写入后120S过期
//                .weakKeys()// key弱引用
                .recordStats());// 统计功能
        return cacheManager;
    }

}
