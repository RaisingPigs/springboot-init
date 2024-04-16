package com.pan.app.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pan.app.common.resp.BizCode;
import com.pan.app.exception.BizException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-02-02 08:33
 **/
public class JSONUtils {
    private static final ObjectMapper OBJECT_MAPPER;
    private static final Logger log = LoggerFactory.getLogger(JSONUtils.class);

    static {
        OBJECT_MAPPER = SpringContextUtils.getBean(ObjectMapper.class);
    }

    private JSONUtils() {
    }

    public static <T> String toJsonStr(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("json转换异常: [{}]", e.getMessage(), e);
            throw new BizException(BizCode.SYSTEM_ERR, "json转换异常: " + e.getMessage());
        }
    }

    public static <T> T toObj(String jsonStr, Class<T> clazz) {
        if (StringUtils.isBlank(jsonStr) || clazz == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(jsonStr, clazz);
        } catch (Exception e) {
            log.error("json转换异常: [{}]", e.getMessage(), e);
            throw new BizException(BizCode.SYSTEM_ERR, "json转换异常: " + e.getMessage());
        }
    }

    public static <T> T toObj(String jsonStr, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(jsonStr) || typeReference == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(jsonStr, typeReference);
        } catch (IOException e) {
            log.error("json转换异常: [{}]", e.getMessage(), e);
            throw new BizException(BizCode.SYSTEM_ERR, "json转换异常: " + e.getMessage());
        }
    }
}
