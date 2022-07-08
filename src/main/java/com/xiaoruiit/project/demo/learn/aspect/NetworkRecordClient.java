package com.xiaoruiit.project.demo.learn.aspect;

import com.xiaoruiit.project.demo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author hanxiaorui
 * @date 2022/7/7
 */
@RpcClient(
        url = "https://cn.bing.com",value = "test"
)
public interface NetworkRecordClient {

    @GetMapping("/")
    void index();
}
