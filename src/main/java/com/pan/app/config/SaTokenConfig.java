package com.pan.app.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfig implements WebMvcConfigurer {
    private static final String[] WHITE_LIST = {
        "/user/login",
        "/user/regist"
    };

    // 注册 Sa-Token 拦截器，打开注解式鉴权功能
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，打开注解式鉴权功能
        registry.addInterceptor(new SaInterceptor())
            .addPathPatterns("/**")
            .excludePathPatterns(WHITE_LIST);
    }

    /*跨域过滤器*/
    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()
            // 前置函数：在每次认证函数之前执行
            .setBeforeAuth(obj -> {
                SaHolder.getResponse()
                    // 允许指定域访问跨域资源
                    .setHeader("Access-Control-Allow-Origin", "*")
                    // 允许所有请求方式
                    .setHeader("Access-Control-Allow-Methods", "*")
                    // 允许的header参数
                    .setHeader("Access-Control-Allow-Headers", "*")
                    // 有效时间
                    .setHeader("Access-Control-Max-Age", "3600");

                // 如果是预检请求，则立即返回到前端 
                SaRouter.match(SaHttpMethod.OPTIONS).back();
            });
    }
}