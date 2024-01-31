package com.pan.app.model.enums.user;

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
public enum RoleEnum {
    ADMIN(0, "管理员"),
    USER(1, "用户");


    private static final Map<Integer, RoleEnum> VALUE_MAP = Arrays.stream(values())
        .collect(Collectors.toMap(
            RoleEnum::getCode,
            Function.identity(),
            (enum1, enum2) -> enum1
        ));
    private final int code;
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
