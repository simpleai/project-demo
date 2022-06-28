package com.xiaoruiit.project.demo.learn.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author hanxiaorui
 * @date 2022/6/27
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    /**
     * 利用guava实现本地缓存 最大400，软引用，缓存1分钟
     */
    private LoadingCache<String, UserVo> productBarCodeVosCache =
            CacheBuilder.newBuilder()
                    .maximumSize(400) // maximum 400 records can be cached
                    .softValues() // soft reference. no RAM, clean productBarCodeVos
                    //.expireAfterAccess(1, TimeUnit.MINUTES) // cache will expire after 1 minutes of access
                    .refreshAfterWrite(1,TimeUnit.SECONDS)
                    .build(new CacheLoader<String, UserVo>(){ // build the cacheloader
                        @Override
                        public UserVo load(String userCode) {
                            return getUserVoByCode(userCode);
                        }
                    });


    @Override
    public UserVo getUserVoByCode(String userCode) {
        UserVo userVo = new UserVo();
        if (userCode == "admin"){
            System.out.println("admin");
            userVo.setUserCode("admin");
            userVo.setUserName("admin");
            return userVo;
        }
        return userVo;
    }

    /**
     * 本地缓存中获取
     *
     * @param userCode
     * @return
     */
    @Override
    public UserVo getUserVoCache(String userCode){
        UserVo productBarCodeVo = null;
        try {
            productBarCodeVo = productBarCodeVosCache.get(userCode);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return productBarCodeVo;
    }
}
