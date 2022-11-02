package com.xiaoruiit.project.demo.learn.transaction;

/**
 * @author hanxiaorui
 * @date 2022/8/3
 */
public class TransactionHookProcessorTest {

    // todo 数据库

    public static void main(String[] args) {

        TransactionHookProcessor transactionHookProcessor = new TransactionHookProcessor();

        transactionHookProcessor.call(() ->{
            // 发mq
            //receProducer.send(message);
        },true);

    }
}
