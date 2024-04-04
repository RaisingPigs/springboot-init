package com.pan.app.model.enums.login;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-03-23 15:40
 **/
@Getter
public enum Type {
    DEFAULT(0, "默认登录"),
    GITEE(1, "gitee登录");

    private static final Map<Integer, Type> VALUE_MAP = Arrays.stream(values())
        .collect(Collectors.toMap(
            Type::getCode,
            Function.identity(),
            (enum1, enum2) -> enum1
        ));
    @EnumValue
    private final int code;
    @JsonValue
    private final String desc;

    Type(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static Type of(int code) {
        return VALUE_MAP.computeIfAbsent(code, __ -> {
            throw new RuntimeException(code + "不存在");
        });
    }

    @Override
    public String toString() {
        return "Type{" +
            "code=" + code +
            ", desc='" + desc + '\'' +
            '}';
    }
}
