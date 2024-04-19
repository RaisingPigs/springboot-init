package com.pan.app.aop;

import com.pan.app.common.param.ParamChecker;
import com.pan.app.common.param.ParamHandler;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-04-18 10:58
 **/
@Aspect
@Component
public class ControllerAop {
    @Pointcut("execution(* com.pan.app.controller.*.*(..))")
    public void controllerAspect() {
    }

    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            if (arg instanceof List) {
                List<?> argList = (List<?>) arg;
                argList.forEach(this::handleParam);
            } else {
                handleParam(arg);
            }
        }
    }

    public void handleParam(Object param) {
        if (param instanceof ParamChecker) {
            ParamChecker paramChecker = (ParamChecker) param;
            paramChecker.checkParam();
        }

        if (param instanceof ParamHandler) {
            ParamHandler paramHandler = (ParamHandler) param;
            paramHandler.handleParam();
        }
    }
}
