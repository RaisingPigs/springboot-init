package com.pan.app;

import com.pan.app.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SpringbootInitApplicationTests {
    @Resource
    private UserService userService;

    @Test
    void contextLoads() {
        System.out.println(userService.list());
    }

}
