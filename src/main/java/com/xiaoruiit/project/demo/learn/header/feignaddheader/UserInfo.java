package com.xiaoruiit.project.demo.learn.header.feignaddheader;

import lombok.Data;
import org.springframework.core.NamedThreadLocal;

/**
 * @author hanxiaorui
 * @date 2022/4/9
 */
@Data
public class UserInfo {

    /**
     * 保证线程安全
     */
    private static final ThreadLocal<UserInfo> THREAD_LOCAL = new NamedThreadLocal<>("branchContext");

    private String userCode;

    private String userName;

    public static UserInfo current() {
        return THREAD_LOCAL.get();
    }

    public static void clean() {
        THREAD_LOCAL.remove();
        THREAD_LOCAL.set(null);
    }

    public static void set(UserInfo userInfo) {
        THREAD_LOCAL.set(userInfo);
    }
}
