package com.pan.app.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;
import java.util.TimeZone;

@Configuration
public class JacksonConfig {
    private static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";


    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            // 这一部分也可以在 yaml 中配置
            builder
                // 序列化时，对象为 null，是否抛异常
                .failOnEmptyBeans(false)
                // 反序列化时，json 中包含 pojo 不存在属性时，是否抛异常
                .failOnUnknownProperties(false)
                // 禁止将 java.util.Date, Calendar 序列化为数字(时间戳)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                // 设置 java.util.Date, Calendar 序列化、反序列化的格式
                .dateFormat(new SimpleDateFormat(DEFAULT_DATETIME_PATTERN))
                // 设置 java.util.Date、Calendar 序列化、反序列化的时区
                .timeZone(TimeZone.getTimeZone("GMT+8"))
            ;

            // Jackson 序列化 long类型为String，解决后端返回的Long类型在前端精度丢失的问题
            builder.serializerByType(BigInteger.class, ToStringSerializer.instance);
            builder.serializerByType(Long.class, ToStringSerializer.instance);
            builder.serializerByType(Long.TYPE, ToStringSerializer.instance);

            // 配置 Jackson 反序列化 LocalDateTime、LocalDate、LocalTime 时使用的格式
            builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATETIME_PATTERN)));
            builder.deserializerByType(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
            builder.deserializerByType(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));

            // 配置 Jackson 序列化 LocalDateTime、LocalDate、LocalTime 时使用的格式
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATETIME_PATTERN)));
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
            builder.serializers(new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        };
    }

    /**
     * 配置jackson反序列化默认值
     * 当String类型的数据为null时, 返回"" 
     * 当Map类型的数据为null时, 返回{} 
     * 当List类型的数据为null时, 输出[] 
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                String fieldName = jsonGenerator.getOutputContext().getCurrentName();
                try {
                    //反射获取字段类型
                    Field field = jsonGenerator.getCurrentValue().getClass().getDeclaredField(fieldName);
                    if (CharSequence.class.isAssignableFrom(field.getType())) {
                        //字符串型空值""
                        jsonGenerator.writeString("");
                        return;
                    } else if (Collection.class.isAssignableFrom(field.getType())) {
                        //列表型空值返回[]
                        jsonGenerator.writeStartArray();
                        jsonGenerator.writeEndArray();
                        return;
                    } else if (Map.class.isAssignableFrom(field.getType())) {
                        //map型空值 或者 bean对象 返回{}
                        jsonGenerator.writeStartObject();
                        jsonGenerator.writeEndObject();
                        return;
                    }
                } catch (NoSuchFieldException ignored) {
                }

                jsonGenerator.writeString("");
            }
        });
        return objectMapper;
    }
}