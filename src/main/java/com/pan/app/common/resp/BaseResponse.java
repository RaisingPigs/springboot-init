package com.pan.app.common.resp;

import com.pan.app.exception.BusinessException;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-10-07 14:49
 **/
@Data
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = 293244422036841199L;

    private int code;
    private T data;
    private String msg;

    /*无参构造是避免jackson转换时, 无法创建创建对象而报错*/
    public BaseResponse() {
    }

    private BaseResponse(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public BaseResponse(ResultCode resultCode, T data) {
        this(resultCode.getCode(), data, resultCode.getMsg());
    }

    public BaseResponse(ResultCode resultCode, String msg) {
        this(resultCode.getCode(), null, msg);
    }

    public BaseResponse(ResultCode resultCode) {
        this(resultCode, resultCode.getMsg());
    }

    public BaseResponse(BusinessException e) {
        this(e.getCode(), null, e.getMessage());
    }

    public boolean successful() {
        return ResultCode.isSuccessful(this.code);
    }

    public boolean empty() {
        return Objects.isNull(this.data);
    }
}

