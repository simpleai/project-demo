package com.xiaoruiit.project.demo.learn.multithread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hanxiaorui
 * @date 2022/12/6
 */
@Configuration
public class ThreadPoolIoExecutorUtil {

    // IO密集型
    private int coreSize = Runtime.getRuntime().availableProcessors() * 2;

    // CPU密集型
    private int spuCoreSize = Runtime.getRuntime().availableProcessors() + 1;

    private int threadMaxSize = 16 < coreSize ? coreSize : 16;

    @Bean("threadPoolIoExecutor")
    public ThreadPoolExecutor threadPoolExecutor() {
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue(1024);
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(coreSize, threadMaxSize, 1, TimeUnit.MINUTES,
                        blockingQueue, new ThreadPoolExecutor.AbortPolicy());

        return threadPoolExecutor;
    }
}
