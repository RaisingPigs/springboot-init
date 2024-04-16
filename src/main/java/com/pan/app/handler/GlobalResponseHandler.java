package com.pan.app.handler;

import com.pan.app.common.resp.BizResp;
import com.pan.app.util.JSONUtils;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Collections;

@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {
    private static final String KNIFE4J_PACKAGE = "org.springdoc";

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        String packageName = methodParameter.getDeclaringClass().getPackage().getName();
        return !packageName.startsWith(KNIFE4J_PACKAGE);
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o instanceof BizResp) {
            BizResp<Object> bizResp = (BizResp) o;
            bizResp.setData(Collections.emptyMap());
            return bizResp;
        }
        if (o instanceof String) {
            return JSONUtils.toJsonStr(BizResp.success(o));
        }
        if (o == null) {
            return BizResp.success(Collections.emptyMap());
        }

        return BizResp.success(o);
    }
}

