package com.pan.app.exception;

import com.pan.app.common.resp.BaseResponse;
import com.pan.app.common.resp.ResultCode;
import com.pan.app.common.resp.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-10-07 14:26
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public <T> BaseResponse<T> businessExceptionHandler(BusinessException e) {
        log.error("全局异常处理BusinessException: [{}]", e.getMessage(), e);
        return ResultUtils.failed(e.getCode(), e.getMessage(), e.getDesc());
    }

    @ExceptionHandler(Exception.class)
    public <T> BaseResponse<T> exceptionHandler(Exception e) {
        log.error("全局异常处理Exception: [{}]", e.getMessage(), e);
        return ResultUtils.failed(ResultCode.SYSTEM_ERR, e.getMessage(), "");
    }
}
