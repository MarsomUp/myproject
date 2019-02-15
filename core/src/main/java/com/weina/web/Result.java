package com.weina.web;

/**
 * @Description:
 * @Author: mayc
 * @Date: 2019/02/13 17:09
 */
public class Result {

    private int code;
    private String msg;
    private Object data;

    public Result() {
        this.code = 200;
        this.msg = "操作成功！";
    }

    public Result(String msg) {
        this.code = 200;
        this.msg = msg;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Object data) {
        this.code = 200;
        this.msg = "操作成功！";
        this.data = data;
    }

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
