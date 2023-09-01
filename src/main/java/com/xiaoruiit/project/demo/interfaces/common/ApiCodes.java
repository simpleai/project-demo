package com.xiaoruiit.project.demo.interfaces.common;

/**
 * 后端返回码
 *
 * 放在统一位置，防止重复
 */
public enum ApiCodes {

    /**
     * 全局返回码
     */
    SUCCESS(200,"请求成功"),
    ERROR(500,"请求失败"),
    PARAM_ERROR(400,"参数检验失败"),
    UNAUTHORIZED(401,"暂未登录或token已过期！"),
    FORBIDDEN(403, "没有相关权限"),

    /**
     * 模块一返回码 1000开始
     */
    TEST(1000,"例子"),
    RESUBMIT(1001,"重复提交"),

    /**
     * 模块二返回码 2000开始
     */
    TEST_TEST(2000,"例子"),

    ;

    int code;
    String msg;

    ApiCodes(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
