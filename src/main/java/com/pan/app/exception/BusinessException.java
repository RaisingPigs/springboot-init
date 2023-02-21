package com.pan.app.exception;

import com.pan.app.common.ResultCode;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-10-07 14:29
 **/
public class BusinessException extends RuntimeException{
    private final int code;
    private final String desc;

    public BusinessException(String msg, int code, String desc) {
        super(msg);
        this.code = code;
        this.desc = desc;
    }

    public BusinessException(ResultCode resultCode, String desc) {
        super(resultCode.getMsg());
        
        this.code = resultCode.getCode();
        this.desc = desc;
    }

    public BusinessException(ResultCode resultCode) {
        this(resultCode, resultCode.getDesc());
    }
    
    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
