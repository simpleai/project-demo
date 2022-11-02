package com.xiaoruiit.project.demo.learn.transaction;

import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 事务提交后执行call方法，可用于事务提交后再发送MQ，防止先消费MQ读取了未提交事务的数据。
 * @author hanxiaorui
 * @date 2022/8/3
 */
@Component
public class TransactionHookProcessor {
    public TransactionHookProcessor() {
    }

    public <T> void call(final Runnable runnable, boolean afterTransactionCommit) {
        if (afterTransactionCommit && TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                public void afterCommit() {
                    runnable.run();
                }
            });
        } else {
            runnable.run();
        }
    }
    
}
