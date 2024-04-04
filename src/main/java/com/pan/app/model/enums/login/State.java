package com.pan.app.model.enums.login;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.pan.app.model.enums.BaseEnum;
import lombok.Getter;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-03-28 14:46
 **/
@Getter
public enum State implements BaseEnum {
    FAILED(0, "登录失败"),
    SUCCESS(1, "登录成功");

    @EnumValue
    private final int code;
    @JsonValue
    private final String desc;

    State(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static State of(boolean b) {
        return b ? SUCCESS : FAILED;
    }
}
