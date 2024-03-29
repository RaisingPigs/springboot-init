package com.pan.app.model.enums.user;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.pan.app.model.enums.BaseEnum;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-01-31 18:19
 **/
@Getter
public enum RoleEnum implements BaseEnum {
    ADMIN(0, "admin"),
    USER(1, "user");

    private static final Map<Integer, RoleEnum> VALUE_MAP = Arrays.stream(values())
        .collect(Collectors.toMap(
            RoleEnum::getCode,
            Function.identity(),
            (enum1, enum2) -> enum1
        ));

    @EnumValue
    private final int code;
    @JsonValue
    private final String desc;

    RoleEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static RoleEnum of(int code) {
        return VALUE_MAP.computeIfAbsent(code, __ -> {
            throw new RuntimeException(code + "不存在");
        });
    }

    public static boolean valid(int code) {
        return Objects.nonNull(of(code));
    }

    public static boolean isAdmin(int code) {
        return of(code) == ADMIN;
    }


    public static List<Integer> getValues() {
        return Arrays.stream(values())
            .map(item -> item.code)
            .collect(Collectors.toList());
    }
}
