package com.pan.app.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.pan.app.common.resp.ResultCode;
import com.pan.app.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-02-02 08:33
 **/
public class JSONUtils {
    private static final ObjectMapper OBJECT_MAPPER;
    private static final Logger log = LoggerFactory.getLogger(JSONUtils.class);

    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static {
        OBJECT_MAPPER = new ObjectMapper();
        //对象的所有字段全部列入
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //取消默认转换timestamps形式
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //忽略空Bean转json的错误
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(STANDARD_FORMAT));
        //忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
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
            throw new BusinessException(ResultCode.SYSTEM_ERR, "json转换异常: " + e.getMessage());
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
            throw new BusinessException(ResultCode.SYSTEM_ERR, "json转换异常: " + e.getMessage());
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
            throw new BusinessException(ResultCode.SYSTEM_ERR, "json转换异常: " + e.getMessage());
        }
    }
}
