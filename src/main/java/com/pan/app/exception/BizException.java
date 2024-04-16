package com.pan.app.exception;

import com.pan.app.common.resp.BizCode;
import lombok.Getter;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-10-07 14:29
 **/
@Getter
public class BizException extends RuntimeException {
    private final int code;

    public BizException(BizCode bizCode, String msg) {
        super(msg);
        this.code = bizCode.getCode();
    }

    public BizException(BizCode bizCode) {
        this(bizCode, bizCode.getMsg());
    }

}
