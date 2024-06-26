package com.pan.app.common.resp;

import lombok.Getter;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-10-07 14:39
 **/
@Getter
public enum BizCode {
    SUCCESS(20000, "success"),
    PARAMS_ERR(40000, "请求参数错误"),
    NULL_ERR(40001, "请求数据为空"),
    NO_LOGIN(40100, "未登录"),
    NO_AUTH(40101, "权限不足"),
    SYSTEM_ERR(50000, "系统内部异常");

    private final int code;
    private final String msg;

    BizCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static boolean isSuccessful(int code) {
        return SUCCESS.code == code;
    }

    public static boolean isFailed(int code) {
        return !isSuccessful(code);
    }
}