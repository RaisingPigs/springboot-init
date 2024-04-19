package com.pan.app.service;

import com.pan.app.model.req.user.UserLoginReq;
import com.pan.app.model.req.user.UserRegistReq;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-02-04 05:23
 **/
public interface LoginService {
    String userLogin(UserLoginReq userLoginReq);

    void userLogout();

    long userRegister(UserRegistReq userRegistReq);
}
