package com.xiaoruiit.project.demo.domain.hr.service;


import com.xiaoruiit.project.demo.interfaces.common.PageResult;
import com.xiaoruiit.project.demo.interfaces.dto.UserDto;

/**
 * @author hanxiaorui
 * @date 2023/8/31
 */
public interface UserService {
    PageResult<UserDto> page();
}
