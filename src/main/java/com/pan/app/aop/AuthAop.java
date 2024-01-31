package com.pan.app.aop;

import com.pan.app.annotation.AuthCheck;
import com.pan.app.common.resp.ResultCode;
import com.pan.app.exception.BusinessException;
import com.pan.app.model.dto.user.UserDTO;
import com.pan.app.model.enums.user.RoleEnum;
import com.pan.app.utils.UserHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
        List<RoleEnum> anyRoleList = Arrays.stream(authCheck.anyRole())
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
        RoleEnum mustRole = authCheck.mustRole();

        /*获取已登录用户*/
        UserDTO userDTO = UserHolder.getUser();
        if (userDTO == null) {
            throw new BusinessException(ResultCode.NO_LOGIN);
        }

        RoleEnum userRole = RoleEnum.of(userDTO.getRole());
        /*拥有任意权限即通过*/
        if (!CollectionUtils.isEmpty(anyRoleList) && !anyRoleList.contains(userRole)) {
            throw new BusinessException(ResultCode.NO_AUTH);
        }

        /*必须有所有权限才通过*/
        if (Objects.nonNull(mustRole) && !Objects.equals(userRole, mustRole)) {
            throw new BusinessException(ResultCode.NO_AUTH);
        }

        /*通过权限校验, 放行*/
        return point.proceed();
    }
}