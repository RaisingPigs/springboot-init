package com.pan.app.annotation;

import com.pan.app.model.enums.operation.BusinessType;
import com.pan.app.model.enums.operation.Method;

import java.lang.annotation.*;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-03-31 16:49
 **/

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {
    BusinessType businessType();

    Method reqMethod();

    String reqModule();
}
