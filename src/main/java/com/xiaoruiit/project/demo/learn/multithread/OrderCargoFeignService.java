package com.xiaoruiit.project.demo.learn.multithread;

import com.google.common.collect.Lists;
import com.xiaoruiit.project.demo.common.Result;
import com.xiaoruiit.project.demo.utils.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hanxiaorui
 * @date 2022/7/6
 */
@Service
@Slf4j
public class OrderCargoFeignService implements OrderCargoFeignClient{

    @Override
    public List<OrderCargoDto> getBatchByCodes(String branchCode, List<String> childSkuCodes) {
        log.info("query goods detail begin skuCode:{},branchCode:{}", branchCode, childSkuCodes);
        Result<List<OrderCargoDto>> batchByCodes = this.getBatchByCodeFeigns(branchCode, childSkuCodes);
        log.info("query goods detail end result:{}", JSON.toJSONString(batchByCodes));
        if (batchByCodes.isOk()) {
            return batchByCodes.getData();
        } else {
            return Lists.newArrayList();
        }
    }

    public Result<List<OrderCargoDto>> getBatchByCodeFeigns(String branchCode, List<String> childSkuCodes) {
        return Result.success(new ArrayList<>());
    }
}
