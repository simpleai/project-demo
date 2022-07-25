package com.xiaoruiit.project.demo.learn.cache.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hanxiaorui
 * @date 2022/6/27
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    /**
     * 利用guava实现本地缓存 最大400，软引用，缓存1分钟
     */
    private final LoadingCache<String, UserVo> productBarCodeVosCache =
            CacheBuilder.newBuilder()
                    .maximumSize(400) // maximum 400 records can be cached
                    .softValues() // soft reference. no RAM, clean productBarCodeVos
                    //.expireAfterAccess(1,TimeUnit.MINUTES) // 在被读或写，等待1分钟后删除，每次读或写更新等待的1分钟。  场景：修改会更新数据，预计的1分钟不使用，之后不在使用
                    //.expireAfterWrite(1, TimeUnit.MINUTES) // 第一次写入后，1分钟后删除。 加载期间，阻塞对缓存的访问。  场景：请求快，允许1分钟的脏数据。
                    .refreshAfterWrite(5,TimeUnit.SECONDS)// 到期后，异步加载值。 适用场景，互联网高并发。 缓存失效读取旧值，同时异步加载新值，需实现reload方法，用线程池异步执行
                    .build(new CacheLoader<String, UserVo>(){ // build the cacheloader
                        private AtomicInteger count = new AtomicInteger(1);

                        @Override
                        public UserVo load(String userCode) {
                            System.out.println(count.incrementAndGet());
                            return getUserVoByCode(userCode);
                        }
                        @Override
                        public ListenableFuture<UserVo> reload(String userCode, UserVo oldUserVo){
                            System.out.println("Reload");
                            count.incrementAndGet();
                            ListenableFutureTask<UserVo> futureTask = ListenableFutureTask.create(() -> {
                                UserVo userVo = new UserVo();
                                userVo.setUserCode("new"+count.get());
                                userVo.setUserName("new");
                                return userVo;
                            });
                            executorService.execute(futureTask);
                            return futureTask;
                        }
                    });


    @Override
    public UserVo getUserVoByCode(String userCode) {
        UserVo userVo = new UserVo();
        System.out.println(userCode);
        try {
            System.out.println("block");
            Thread.currentThread().sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if ("admin".equals(userCode)){
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
