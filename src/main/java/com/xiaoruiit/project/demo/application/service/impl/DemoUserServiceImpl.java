package com.xiaoruiit.project.demo.application.service.impl;

import com.xiaoruiit.project.demo.application.service.DemoUserService;
import org.springframework.stereotype.Service;

/**
 * @author hanxiaorui
 * @date 2021/12/22
 */
@Service
public class DemoUserServiceImpl implements DemoUserService {



    @Override
    public String getUser(String userId) {
        return "user";
    }
}
