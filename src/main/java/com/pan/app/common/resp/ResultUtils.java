package com.pan.app.common.resp;

import com.pan.app.exception.BusinessException;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-10-07 14:46
 **/
public class ResultUtils {
    public static <T> BaseResponse<T> success() {
        return new BaseResponse<>(ResultCode.SUCCESS);
    }
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(ResultCode.SUCCESS, data);
    }

    public static <T> BaseResponse<T> failed(ResultCode resultCode) {
        return new BaseResponse<>(resultCode);
    }

    public static <T> BaseResponse<T> failed(ResultCode resultCode, String msg) {
        return new BaseResponse<>(resultCode, msg);
    }

    public static <T> BaseResponse<T> failed(BusinessException e) {
        return new BaseResponse<>(e);
    }
}
