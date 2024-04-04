package com.pan.app.model.enums.operation;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.pan.app.model.enums.BaseEnum;
import lombok.Getter;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-03-31 17:03
 **/
@Getter
public enum BusinessType implements BaseEnum {
    OTHER(0, "其他"),
    INSERT(1, "新增"),
    DELETE(2, "删除"),
    UPDATE(3, "修改"),
    SELECT(4, "查询"),
    ;


    @EnumValue
    private final int code;
    @JsonValue
    private final String desc;

    BusinessType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
