package com.pan.app.exception;

import com.pan.app.common.BaseResponse;
import com.pan.app.common.ResultCode;
import com.pan.app.common.ResultUtils;
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
        log.error("" + e.getMessage(), e);
        return ResultUtils.failed(e.getCode(), e.getMessage(), e.getDesc());
    }

    @ExceptionHandler(Exception.class)
    public <T> BaseResponse<T> exceptionHandler(Exception e) {
        log.error("" + e.getMessage(), e);
        return ResultUtils.failed(ResultCode.SYSTEM_ERR, e.getMessage(), "");
    }
}
