package com.pan.app.common.resp;

import lombok.Getter;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-10-07 14:39
 **/
@Getter
public enum ResultCode {
    SUCCESS(20000, "success"),
    PARAMS_ERR(40000, "请求参数错误"),
    NULL_ERR(40001, "请求数据为空"),
    NO_LOGIN(40100, "未登录"),
    NO_AUTH(40101, "权限不足"),
    SYSTEM_ERR(50000, "系统内部异常"),
    SAVE_ERR(50010, "新增失败"),
    UPDATE_ERR(50020, "更新失败"),
    DELETE_ERR(50030, "删除失败");

    private final int code;
    private final String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}