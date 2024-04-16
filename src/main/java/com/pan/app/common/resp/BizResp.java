package com.pan.app.common.resp;

import com.pan.app.exception.BizException;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-10-07 14:49
 **/
@Data
public class BizResp<T> implements Serializable {
    private static final long serialVersionUID = 293244422036841199L;

    private int code;
    private T data;
    private String msg;

    /*无参构造是避免jackson转换时, 无法创建创建对象而报错*/
    public BizResp() {
    }

    private BizResp(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public BizResp(BizCode bizCode, T data) {
        this(bizCode.getCode(), data, bizCode.getMsg());
    }

    public BizResp(BizCode bizCode, String msg) {
        this(bizCode.getCode(), null, msg);
    }

    public BizResp(BizCode bizCode) {
        this(bizCode, bizCode.getMsg());
    }

    public BizResp(BizException e) {
        this(e.getCode(), null, e.getMessage());
    }

    public static <D> BizResp<D> success() {
        return new BizResp<>(BizCode.SUCCESS);
    }

    public static <D> BizResp<D> success(D data) {
        return new BizResp<>(BizCode.SUCCESS, data);
    }

    public static <D> BizResp<D> failed(BizCode bizCode) {
        return new BizResp<>(bizCode);
    }

    public static <D> BizResp<D> failed(BizCode bizCode, String msg) {
        return new BizResp<>(bizCode,  msg);
    }

    public static <T> BizResp<T> failed(BizException e) {
        return new BizResp<>(e);
    }

    public boolean successful() {
        return BizCode.isSuccessful(this.code);
    }

    public boolean empty() {
        return Objects.isNull(this.data);
    }
}

