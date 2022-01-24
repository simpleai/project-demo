package com.xiaoruiit.project.demo.common;

import java.io.Serializable;

/**
 * 后端返回封装
 *
 * @author hxr
 * @param <T>
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;
    private T data;

    public Result() {
        this.code = ApiCodes.SUCCESS.getCode();
        this.msg = ApiCodes.SUCCESS.getMsg();
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        return success(ApiCodes.SUCCESS.getMsg(), data);
    }

    public static <T> Result<T> successMsg(String msg) {
        return success(msg, null);
    }

    public static <T> Result<T> success(String msg, T data) {
        return new Result(ApiCodes.SUCCESS.getCode(), msg, data);
    }

    public static <T> Result<T> error() {
        return error(ApiCodes.ERROR.getMsg());
    }

    public static <T> Result<T> error(String msg) {
        return error(ApiCodes.ERROR.getCode(), msg);
    }

    public static <T> Result<T> error(int code, String message) {
        return new Result(code, message, null);
    }


    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
