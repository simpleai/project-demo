package com.xiaoruiit.project.demo.controller;

import com.xiaoruiit.project.demo.service.DemoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hanxiaorui
 * @date 2021/12/22
 */

@RestController()
@RequestMapping("/demo/user")
//@Api("测试swagger")
public class DemoUserController {

    @Autowired
    private DemoUserService demoUserService;

    @GetMapping("/getUser")
    //@ApiOperation(value = "获取user", notes = "获取user",httpMethod = "GET")
    public String demoUser(@RequestParam("userId") String userId){
        return demoUserService.getUser(userId);
    }
}
