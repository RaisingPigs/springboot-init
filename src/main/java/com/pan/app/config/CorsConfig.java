package com.pan.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2023-02-21 16:02
 **/
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                /*是否允许发送cookie*/
                .allowCredentials(true)
                /*放行哪些域名*/
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                /*放行哪些原始请求头部信息*/
                .allowedHeaders("*")
                /*暴露哪些原始请求头部信息*/
                .exposedHeaders("*");
    }
}
