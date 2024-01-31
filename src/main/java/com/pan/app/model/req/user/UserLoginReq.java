package com.pan.app.model.req.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2023-02-21 16:33
 **/
@Data
public class UserLoginReq implements Serializable {
    /**
     * 账号
     */
    private String account;
    
    /**
     * 密码
     */
    private String password;
    
    private static final long serialVersionUID = 1L;
}
