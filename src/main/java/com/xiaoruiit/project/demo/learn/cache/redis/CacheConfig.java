//package com.xiaoruiit.project.demo.learn.cache.redis;
//
//import com.github.benmanes.caffeine.cache.Caffeine;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.caffeine.CaffeineCacheManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.cache.RedisCacheWriter;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import org.springframework.data.redis.serializer.RedisSerializer;
//
//import java.time.Duration;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author hanxiaorui
// * @date 2022/7/8
// */
//@EnableCaching
//@Configuration
//public class CacheConfig {
//
//    @Bean("branchExclusively")
//    @Primary
//    public CacheManager cacheExclusivelyManager() {
//        CaffeineCacheManager cacheManager = new CaffeineCacheManager("branchExclusivelys");
//        cacheManager.setCaffeine(Caffeine.newBuilder()
//                .initialCapacity(10000)
//                .expireAfterWrite(120, TimeUnit.SECONDS)
////                .weakKeys()
//                .recordStats());
//        return cacheManager;
//    }
//
//    /**
//     * 使用redis作缓存
//     * @param factory
//     * @return
//     */
//    @Bean(value = "pointMallManager",autowireCandidate=false)
//    public CacheManager pointMallCacheManager(RedisConnectionFactory factory) {
//        //初始化一个RedisCacheWriter
//        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(factory);
//        //设置CacheManager的值序列化方式为json序列化
//        RedisSerializer<Object> jsonSerializer = new GenericJackson2JsonRedisSerializer();
//        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer);
//        //设置默认超过期时间是600秒,不缓存null值
//        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair).entryTtl(Duration.ofSeconds(600)).disableCachingNullValues();
//        RedisCacheManager redisCacheManager = new RedisCacheManager(redisCacheWriter, defaultCacheConfig);
///*        CaffeineCacheManager cacheManager = new CaffeineCacheManager("pointMall", "allSpecRemark");
//        cacheManager.setCaffeine(Caffeine.newBuilder()
//                .initialCapacity(500)
//                .expireAfterWrite(10, TimeUnit.MINUTES)
////                .weakKeys()
//                .recordStats());*/
//
//        return redisCacheManager;
//    }
//
//}
