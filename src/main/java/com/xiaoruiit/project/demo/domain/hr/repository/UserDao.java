package com.xiaoruiit.project.demo.domain.hr.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoruiit.project.demo.infrastructure.driver.entity.User;
import com.xiaoruiit.project.demo.infrastructure.driver.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author hanxiaorui
 * @date 2023/8/31
 */
@Service
public class UserDao extends ServiceImpl<UserMapper, User> implements IService<User> {
}
