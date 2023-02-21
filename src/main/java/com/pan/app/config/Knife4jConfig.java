package com.pan.app.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-10-07 16:55
 **/
@Configuration
@EnableKnife4j
public class Knife4jConfig {
    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                //=====================此处需要修改basePackage================
                .apis(RequestHandlerSelectors.basePackage("com.pan.app.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    public ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("<<SpringBoot项目模板>>的后端接口文档")
                .description("此文档说明了SpringBoot项目模板的后端接口规范")
                .termsOfServiceUrl("http://localhost:9999/")
                .version("1.0.0")
                .contact(new Contact("pan", "localhost:9999", "pan@QQ.com"))
                .build();
    }
}
