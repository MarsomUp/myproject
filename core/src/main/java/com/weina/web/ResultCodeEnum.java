package com.weina.web;

/**
 * @Description:
 * @Author: mayc
 * @Date: 2019/02/13 17:21
 */
public enum ResultCodeEnum {
    OK(200, "SUCCESS", "操作成功"),
    SYSTEM_ERROR(500, "SYSTEM_ERROR", "系统错误"),
    DATABASE_ERROR(1000, "DATABASE_ERROR", "数据库错误"),
    PARAMETER_ERROR(2000, "PARAMETER_ERROR", "参数错误");

    public int code;
    public String desc;
    public String msg;

    ResultCodeEnum() {}

    ResultCodeEnum(int code, String desc, String msg) {
        this.code = code;
        this.desc = desc;
        this.msg = msg;
    }
}
