package com.pan.app.aop;

import cn.hutool.extra.servlet.ServletUtil;
import com.pan.app.annotation.OperationLog;
import com.pan.app.common.resp.BizResp;
import com.pan.app.event.OperationLogEvent;
import com.pan.app.model.dto.user.UserDTO;
import com.pan.app.model.entity.SysOperationLog;
import com.pan.app.model.enums.operation.BusinessType;
import com.pan.app.model.enums.operation.Method;
import com.pan.app.util.AuthUtils;
import com.pan.app.util.EventPublishUtils;
import com.pan.app.util.JSONUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-03-31 16:49
 **/
@Aspect
@Component
public class OperationLogAop {
    @Pointcut(value = "@annotation(operationLog)", argNames = "operationLog")
    public void operationLogAspect(OperationLog operationLog) {
    }

    @AfterReturning(value = "operationLogAspect(operationLog)", argNames = "joinPoint,operationLog,returnValue", returning = "returnValue")
    public void doAfterReturning(JoinPoint joinPoint, OperationLog operationLog, BizResp<?> returnValue) {
        SysOperationLog sysOperationLog = createSysOperationLog(joinPoint, operationLog, JSONUtils.toJsonStr(returnValue));
        EventPublishUtils.publishEvent(new OperationLogEvent<>(this, sysOperationLog));
    }

    @AfterThrowing(value = "operationLogAspect(operationLog)", argNames = "joinPoint,operationLog,e", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, OperationLog operationLog, Exception e) {
        SysOperationLog sysOperationLog = createSysOperationLog(joinPoint, operationLog, e.toString());
        EventPublishUtils.publishEvent(new OperationLogEvent<>(this, sysOperationLog));
    }

    private SysOperationLog createSysOperationLog(JoinPoint joinPoint, OperationLog operationLog, String reqResult) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.notNull(requestAttributes, "requestAttributes为null");
        HttpServletRequest request = requestAttributes.getRequest();

        BusinessType businessType = operationLog.businessType();
        Method reqMethod = operationLog.reqMethod();
        String reqModule = operationLog.reqModule();
        String url = request.getRequestURI();
        String callMethod = joinPoint.getSignature().getName();
        UserDTO loginUser = AuthUtils.getLoginUser();

        String ip = ServletUtil.getClientIP(request);

        String reqParam = JSONUtils.toJsonStr(joinPoint.getArgs());

        return new SysOperationLog(businessType, reqMethod, reqModule, url, callMethod, loginUser.getId(), loginUser.getUsername(), ip, reqParam, reqResult);
    }
}
