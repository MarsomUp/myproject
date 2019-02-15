package com.weina.exception;

/**
 * @Description:
 * @Author: mayc
 * @Date: 2019/02/13 10:18
 */
public class BusinessException extends RuntimeException {
    private int code;
    private String msg;
    private Throwable throwable;

    public BusinessException() {
        super();
    }

    public BusinessException(int code) {
        super();
        this.code = code;
    }

    public BusinessException(String msg) {
        super();
        this.msg = msg;
    }

    public BusinessException(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(Throwable throwable) {
        super();
        this.throwable = throwable;
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

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
