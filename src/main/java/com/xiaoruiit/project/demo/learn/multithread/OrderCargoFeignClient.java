package com.xiaoruiit.project.demo.learn.multithread;

import com.xiaoruiit.project.demo.common.Result;

import java.util.List;

/**
 * @author hanxiaorui
 * @date 2022/7/6
 */
public interface OrderCargoFeignClient {

    List<OrderCargoDto> getBatchByCodes(String branchCode, List<String> childSkuCodes);
}
