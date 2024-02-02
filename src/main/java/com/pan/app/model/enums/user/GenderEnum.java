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
 * @create: 2023-02-21 21:41
 **/
@Getter
public enum GenderEnum implements BaseEnum {
    MALE(0, "男"),
    FEMALE(1, "女");

    private static final Map<Integer, GenderEnum> VALUE_MAP = Arrays.stream(values())
        .collect(Collectors.toMap(
            GenderEnum::getCode,
            Function.identity(),
            (enum1, enum2) -> enum1
        ));
    
    @EnumValue
    private final int code;
    @JsonValue
    private final String desc;


    GenderEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static GenderEnum of(int code) {
        return VALUE_MAP.computeIfAbsent(code, __ -> {
            throw new RuntimeException(code + "不存在");
        });
    }

    public static boolean valid(int code) {
        return Objects.nonNull(of(code));
    }


    public static List<Integer> getValues() {
        return Arrays.stream(values())
            .map(item -> item.code)
            .collect(Collectors.toList());
    }

}
