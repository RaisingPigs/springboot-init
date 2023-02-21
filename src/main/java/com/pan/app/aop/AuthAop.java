package com.pan.app.aop;

import com.pan.app.annotation.AuthCheck;
import com.pan.app.common.ResultCode;
import com.pan.app.exception.BusinessException;
import com.pan.app.model.vo.UserVo;
import com.pan.app.utils.UserHolder;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2023-02-21 23:54
 **/
@Aspect
@Component
public class AuthAop {
    @Around("@annotation(authCheck)")
    public Object doAuthCheck(
            ProceedingJoinPoint point,
            AuthCheck authCheck) throws Throwable {
        /*获取注解信息*/
        List<String> anyRoleList = Arrays.stream(authCheck.anyRole())
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList());
        String mustRole = authCheck.mustRole();

        /*获取已登录用户*/
        UserVo user = UserHolder.getUser();
        if (user == null) {
            throw new BusinessException(ResultCode.NO_LOGIN);
        }
        
        /*拥有任意权限即通过*/
        if (!CollectionUtils.isEmpty(anyRoleList)) {
            String userRole = user.getUserRole();
            if (!anyRoleList.contains(userRole)) {
                throw new BusinessException(ResultCode.NO_AUTH);
            }
        }
        
        /*必须有所有权限才通过*/
        if (StringUtils.isNotBlank(mustRole)) {
            String userRole = user.getUserRole();
            if (!mustRole.equals(userRole)) {
                throw new BusinessException(ResultCode.NO_AUTH);
            }
        }
        
        /*通过权限校验, 放行*/
        return point.proceed();
    }
}
