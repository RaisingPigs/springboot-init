package com.pan.app.common;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-10-07 14:46
 **/
public class ResultUtils {
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<T>(ResultCode.SUCCESS, data);
    }

    public static <T> BaseResponse<T> failed(ResultCode resultCode) {
        return new BaseResponse<>(resultCode);
    }

    public static <T> BaseResponse<T> failed(ResultCode resultCode, String msg, String desc) {
        return new BaseResponse<>(resultCode, msg, desc);
    }

    public static <T> BaseResponse<T> failed(ResultCode resultCode, String desc) {
        return new BaseResponse<>(resultCode, desc);
    }

    public static <T> BaseResponse<T> failed(int code, String msg, String desc) {
        return new BaseResponse<>(code, null, msg, desc);
    }
}
