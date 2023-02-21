package com.pan.app.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2023-02-21 23:33
 **/
@Aspect
@Component
@Slf4j
public class InterfaceLogAop {
    @Around("execution(* com.pan.app.controller.*.*(..))")
    public Object logInterface(ProceedingJoinPoint point) throws Throwable {
        /*计时*/
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        /*获取请求路径*/
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        /*生成唯一请求id*/
        String reqId = UUID.randomUUID().toString();
        String url = request.getRequestURI();

        /*获取请求参数*/
        Object[] args = point.getArgs();
        String reqParam = "[" + StringUtils.join(args, ", ") + "]";

        /*输出请求日志*/
        log.info("request start. id: {}, url: {}, ip: {}, params: {}",
                reqId, url, request.getRemoteHost(), reqParam);

        /*执行原方法*/
        Object res = point.proceed();

        /*输出响应日志*/
        stopWatch.stop();
        long timeMillis = stopWatch.getTotalTimeMillis();
        log.info("request end. id: {}, cost: {}ms",
                reqId, timeMillis);

        return res;
    }
}
