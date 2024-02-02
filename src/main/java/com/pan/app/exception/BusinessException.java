package com.pan.app.exception;

import com.pan.app.common.resp.ResultCode;
import lombok.Getter;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-10-07 14:29
 **/
@Getter
public class BusinessException extends RuntimeException {
    private final int code;

    public BusinessException(ResultCode resultCode, String msg) {
        super(msg);
        this.code = resultCode.getCode();
    }

    public BusinessException(ResultCode resultCode) {
        this(resultCode, resultCode.getMsg());
    }

}
