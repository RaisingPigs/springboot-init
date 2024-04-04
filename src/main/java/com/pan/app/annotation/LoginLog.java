package com.pan.app.annotation;


import com.pan.app.model.enums.login.Type;

import java.lang.annotation.*;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-03-27 20:37
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginLog {
    Type loginType() default Type.DEFAULT;

    String username() default "";
}
