package com.pan.app.service;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-02-04 05:23
 **/
public interface LoginService {
    String userLogin(String username, String password);

    void userLogout();

    long userRegister(String username, String password, String checkPassword);
}
