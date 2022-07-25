package com.xiaoruiit.project.demo.learn.cache.guava;


/**
 * @author hxr
 * @version 1.0
 */
public interface UserService {

    UserVo getUserVoByCode(String userCode);

    UserVo getUserVoCache(String userCode);
}
