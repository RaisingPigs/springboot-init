package com.pan.app.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.exception.SaTokenException;
import com.pan.app.common.resp.BizCode;
import com.pan.app.common.resp.BizResp;
import com.pan.app.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-10-07 14:26
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private static final String ERR_MSG_DELIMITER = ",";


    @ExceptionHandler(BizException.class)
    public BizResp<?> businessExceptionHandler(BizException e) {
        log.error("全局异常处理BusinessException: [{}]", e.getMessage(), e);
        return BizResp.failed(e);
    }

    @ExceptionHandler(SaTokenException.class)
    public BizResp<?> businessExceptionHandler(SaTokenException e) {
        log.error("全局异常处理ConstraintViolationException: [{}]", e.getMessage(), e);
        // 不同异常返回不同状态码
        if (e instanceof NotLoginException) {               // 如果是未登录异常
            return BizResp.failed(BizCode.NO_LOGIN);
        }
        if (e instanceof NotRoleException) {         // 如果是角色异常
            return BizResp.failed(BizCode.NO_AUTH);
        }
        if (e instanceof NotPermissionException) {   // 如果是权限异常
            return BizResp.failed(BizCode.NO_AUTH);
        }

        // 其他异常
        return BizResp.failed(BizCode.NO_AUTH);
    }

    /**
     * 处理 json 请求体调用接口对象参数校验失败抛出的异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BizResp<?> jsonParamsException(MethodArgumentNotValidException e) {
        String errMsg = e.getBindingResult().getFieldErrors().stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.joining(ERR_MSG_DELIMITER));

        log.error("全局异常处理MethodArgumentNotValidException: [{}]", errMsg, e);

        return BizResp.failed(BizCode.SYSTEM_ERR, errMsg);
    }


    /**
     * 处理单个参数校验失败抛出的异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public BizResp<?> ParamsException(ConstraintViolationException e) {
        String errMsg = e.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.joining(ERR_MSG_DELIMITER));

        log.error("全局异常处理ConstraintViolationException: [{}]", errMsg, e);
        return BizResp.failed(BizCode.SYSTEM_ERR, errMsg);
    }

    /**
     * @param e
     * @return 处理 form data方式调用接口对象参数校验失败抛出的异常
     */
    @ExceptionHandler(BindException.class)
    public BizResp<?> formDaraParamsException(BindException e) {
        String errMsg = e.getBindingResult().getFieldErrors().stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.joining(ERR_MSG_DELIMITER));

        log.error("全局异常处理BindException: [{}]", errMsg, e);
        return BizResp.failed(BizCode.SYSTEM_ERR, errMsg);
    }

    @ExceptionHandler(Exception.class)
    public BizResp<?> exceptionHandler(Exception e) {
        log.error("全局异常处理Exception: [{}]", e.getMessage(), e);
        return BizResp.failed(BizCode.SYSTEM_ERR, e.getMessage());
    }
}
