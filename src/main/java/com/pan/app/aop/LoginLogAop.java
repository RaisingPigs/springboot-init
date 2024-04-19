package com.pan.app.aop;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.pan.app.annotation.LoginLog;
import com.pan.app.constant.RequestConstant;
import com.pan.app.event.LoginLogEvent;
import com.pan.app.model.dto.user.UserDTO;
import com.pan.app.model.entity.SysLoginLog;
import com.pan.app.model.enums.login.State;
import com.pan.app.model.enums.login.Type;
import com.pan.app.util.AuthUtils;
import com.pan.app.util.EventPublishUtils;
import com.pan.app.util.SpelUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-03-27 20:44
 **/
@Aspect
@Component
public class LoginLogAop {
    @Pointcut(value = "@annotation(loginLog)", argNames = "loginLog")
    public void loginLogAspect(LoginLog loginLog) {
    }

    @AfterReturning(value = "loginLogAspect(loginLog)", argNames = "joinPoint,loginLog,returnValue", returning = "returnValue")
    public void doAfterReturning(JoinPoint joinPoint, LoginLog loginLog, Object returnValue) {
        UserDTO loginUser = AuthUtils.getLoginUser();
        Long userId = loginUser.getId();
        String username = loginUser.getUsername();
        State status = State.SUCCESS;
        String msg = State.SUCCESS.getDesc();
        
        SysLoginLog sysLoginLog = createSysLoginLog(userId, username, loginLog, status, msg);
        EventPublishUtils.publishEvent(new LoginLogEvent<>(this, sysLoginLog));
    }

    @AfterThrowing(value = "loginLogAspect(loginLog)", argNames = "joinPoint,loginLog,e", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, LoginLog loginLog, Exception e) {
        String[] paramNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Object[] paramValues = joinPoint.getArgs();

        String username = SpelUtils.parse(loginLog.username(), paramNames, paramValues);

        SysLoginLog sysLoginLog = createSysLoginLog(null, username, loginLog, State.FAILED, e.getMessage());

        EventPublishUtils.publishEvent(new LoginLogEvent<>(this, sysLoginLog));
    }

    private SysLoginLog createSysLoginLog(Long userId, String username, LoginLog loginLog, State status, String msg) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.notNull(requestAttributes, "requestAttributes为null");
        HttpServletRequest request = requestAttributes.getRequest();

        Type loginType = loginLog.loginType();
        String ip = ServletUtil.getClientIP(request);
        UserAgent userAgent = UserAgentUtil.parse(request.getHeader(RequestConstant.USER_AGENT));
        String browser = userAgent.getBrowser().getName();
        String os = userAgent.getOs().getName();

        return new SysLoginLog(userId, username, loginType, ip, browser, os, status, msg);
    }
}
