package com.pan.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringbootInitApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootInitApplication.class, args);
    }

}
