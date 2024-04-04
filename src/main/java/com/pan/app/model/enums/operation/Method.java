package com.pan.app.model.enums.operation;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.pan.app.model.enums.BaseEnum;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2023-03-06 12:12
 **/
@Getter
public enum Method implements BaseEnum {
    GET("GET", 0),
    POST("POST", 1),
    PUT("PUT", 2),
    DELETE("DELETE", 3);

    private static final Map<Integer, Method> VALUE_MAP = Arrays.stream(values())
        .collect(Collectors.toMap(
            Method::getCode,
            Function.identity(),
            (enum1, enum2) -> enum1
        ));
    @EnumValue
    private final int code;
    @JsonValue
    private final String desc;


    Method(String desc, int code) {
        this.desc = desc;
        this.code = code;
    }

    public static Method of(int code) {
        return VALUE_MAP.computeIfAbsent(code, __ -> {
            throw new RuntimeException(code + "不存在");
        });
    }

}
