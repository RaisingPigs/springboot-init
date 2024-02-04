package com.pan.app.service;

import com.pan.app.constant.UserConstant;
import com.pan.app.utils.AuthUtils;
import org.junit.jupiter.api.Test;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-01-31 18:54
 **/
public class ServiceTest {
    @Test
    void test() {
        String encryptPassword = AuthUtils.encryptPassword(UserConstant.DEFAULT_PASSWORD);

        System.out.println(encryptPassword);
    }
}
