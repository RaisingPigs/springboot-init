package com.pan.app.annotation;

import com.pan.app.model.enums.user.RoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2023-02-21 23:51
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {
    /**
     * 任何一个角色
     */
    RoleEnum[] anyRole() default {};

    /**
     * 必须是某个角色
     */
    RoleEnum mustRole() default RoleEnum.ADMIN;
}