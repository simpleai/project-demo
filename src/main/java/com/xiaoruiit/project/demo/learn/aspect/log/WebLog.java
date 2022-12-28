package com.xiaoruiit.project.demo.learn.aspect.log;

import lombok.Data;

/**
 * @author hanxiaorui
 * @date 2022/12/27
 */
@Data
public class WebLog {

    private String userCode;

    private String userName;

    /**
     * 类名+方法名
     */
    private String classMethodName;

    /**
     * 请求参数
     */
    private Object[] requestParam;

    private Object reponseParam;

}
