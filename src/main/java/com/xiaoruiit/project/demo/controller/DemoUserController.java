package com.xiaoruiit.project.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hanxiaorui
 * @date 2021/12/22
 */

@RestController()
@RequestMapping("/demo/user")
public class DemoUserController {

    @GetMapping("/getUser")
    public String demoUser(){
        return "user";
    }
}
