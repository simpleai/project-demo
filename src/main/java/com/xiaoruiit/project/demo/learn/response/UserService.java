package com.xiaoruiit.project.demo.learn.response;

import com.xiaoruiit.project.demo.common.Result;

import java.util.List;

/**
 * @author hanxiaorui
 * @date 2022/12/28
 */
public interface UserService {
    Result<List<?>> findByCode(String code, UserInfoDtoEnum returnType);
}
