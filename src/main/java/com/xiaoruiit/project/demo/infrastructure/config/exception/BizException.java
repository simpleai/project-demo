package com.xiaoruiit.project.demo.infrastructure.config.exception;

/**
 * @author hanxiaorui
 * @date 2022/6/27
 */
public class BizException extends RuntimeException {
    private static final long serialVersionUID = -7443213283905815106L;
    private int code;

    public BizException() {
    }

    public BizException(int code) {
        super("code=" + code);
        this.code = code;
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    public static BizException buildMessage(String message) {
        return new BizException(message);
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
