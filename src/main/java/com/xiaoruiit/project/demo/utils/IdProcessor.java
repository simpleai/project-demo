package com.xiaoruiit.project.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @author hxr
 * @description: 自增id生成，利用redis原子自增类
 */
@Service
public class IdProcessor {

    /**
     * 日期，每次取redis编号时对比日期，发现不同设置id为1
     */
    private static final String CACHE_KEY_USER_NO_DATE = "pss:taskNo:date:";

    private static Long MOD = 60*60*24L;

    /** key过期时间，秒 **/
    private static Long EXPIRE_TIME = 60*60*30L;// ? 30小时

    /**
     * redis自增原子类
     */
    private static RedisAtomicLong entityIdCounter;

    /**
     * 每一部分向左的位移
     */
    private final static long DIFF_DAYS_LEFT = 26;


    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @PostConstruct
    public void init(){
        String yyyyMMdd = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String key = CACHE_KEY_USER_NO_DATE + yyyyMMdd;
        entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
    }

    /**
     *  根据key获取redis值,对比key值，如果不同重新初始化新的自增长redis类
     * @param key
     * @param liveTime
     * @return
     */
    public Long getIncrement(String key, long liveTime) {
        if(!key.equals(entityIdCounter.getKey())){
            synchronized (IdProcessor.class){// 锁类，相当于锁静态方法
                if(!key.equals(entityIdCounter.getKey())){
                    entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
                    entityIdCounter.expire(liveTime, TimeUnit.SECONDS);
                }
            }
        }
        Long increment = entityIdCounter.incrementAndGet();
        return increment;
    }


    /**
     * 获取Task编号
     * 使用Long型低位40位作为数据存储位， 低位26位为自增长id位，高14位为时间差值位，40位的表示最长字符串长度为13位
     * 支持每天 134217727(1<<27 -1 )个会员编号生成
     * 支持32767(1 << 15 - 1)天即 80年的运行时长
     * 目前每日生成的号为12位，大概2053年后增长到13位，
     */
    public Long buildTaskNo(Integer taskSource){
        LocalDate now = LocalDate.now();
        String yyMMdd = now.format(DateTimeFormatter.ofPattern("yyMMdd"));
        String key = CACHE_KEY_USER_NO_DATE + yyMMdd;
        Long val = getIncrement(key, EXPIRE_TIME);
        Long taskNo = Long.valueOf(yyMMdd+taskSource+String.format("%06d", val));
        return taskNo;
    }

}
