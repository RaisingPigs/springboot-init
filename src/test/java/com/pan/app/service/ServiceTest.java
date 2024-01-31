package com.pan.app.service;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Test;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-01-31 18:54
 **/
public class ServiceTest {
    @Test
    void test() {
        String encryptPassword = DigestUtils.md5DigestAsHex(("pan" + "admin").getBytes(StandardCharsets.UTF_8));
        System.out.println(encryptPassword);
    }
}
