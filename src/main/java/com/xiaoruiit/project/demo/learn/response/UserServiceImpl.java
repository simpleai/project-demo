package com.xiaoruiit.project.demo.learn.response;

import com.xiaoruiit.project.demo.common.Result;
import com.xiaoruiit.project.demo.utils.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hanxiaorui
 * @date 2022/12/28
 */
@Service
public class UserServiceImpl implements UserService{
    @Override
    public Result<List<?>> findByCode(String code, UserInfoDtoEnum returnType) {
        // redis或数据库查询
        List<Object> objects = new ArrayList<>();

        List<?> users = EntityUtils.copy(objects, returnType.getClazz());
        return Result.success(users);
    }
}
