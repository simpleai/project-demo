package com.xiaoruiit.project.demo.domain.hr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoruiit.project.demo.domain.hr.repository.UserDao;
import com.xiaoruiit.project.demo.domain.hr.service.UserService;
import com.xiaoruiit.project.demo.infrastructure.driver.entity.User;
import com.xiaoruiit.project.demo.infrastructure.utils.EntityUtils;
import com.xiaoruiit.project.demo.interfaces.common.PageResult;
import com.xiaoruiit.project.demo.interfaces.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hanxiaorui
 * @date 2023/8/31
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public PageResult<UserDto> page() {
        Page<User> page = new Page<>(1L, 10L, true);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 使用动态条件构造器添加查询条件
        queryWrapper.lambda()
                .like(User::getUsername, "a");

        userDao.page(page, queryWrapper);

        PageResult<UserDto> result = new PageResult();

        List<UserDto> userDtos = EntityUtils.copy(page.getRecords(), UserDto.class);

        result.setList(userDtos);
        result.setTotalCount((int) page.getTotal());
        result.setPageNum(1);
        result.setPageSize(10);

        return result;
    }
}
