package com.xiaoruiit.project.demo.learn.multithread;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 多线程分批调用
 *
 * @author hanxiaorui
 * @date 2022/7/6
 */
@Slf4j
public class MultiThreadPartialCalls {

    @Autowired
    private OrderCargoFeignClient orderCargoFeignClient;

    @Resource(name = "threadPoolIoExecutor")
    private ThreadPoolExecutor threadPoolExecutor;

    public List<OrderCargoDto> getBatchByCodes(String branchCode, List<String> skuCodes) {
        // 返回结果定义
        List<OrderCargoDto> orderCargoDtos = new ArrayList<>();

        // 用工具类分批
        List<List<String>> splitList = ListUtils.partition(skuCodes, 100);


        List<Future<List<OrderCargoDto>>> futureList = new ArrayList<>();
        // 分批调用
        splitList.forEach(childSkuCodes -> {
            Future<List<OrderCargoDto>> future = threadPoolExecutor.submit(() -> {
                return orderCargoFeignClient.getBatchByCodes(branchCode, childSkuCodes);// 获取sku信息
            });
            futureList.add(future);
        });
        // 调用结果组装
        for (Future<List<OrderCargoDto>> future : futureList) {
            try {
                orderCargoDtos.addAll(future.get());
            } catch (InterruptedException e) {
                log.error(e.toString());
            } catch (ExecutionException e) {
                log.error(e.toString());
            }
        }

        return orderCargoDtos;
    }
}
