package com.pan.app.model.req.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2023-02-21 16:34
 **/
@Data
public class UserRegistReq implements Serializable {
    private String account;

    private String password;
    
    private String checkPassword;

    private static final long serialVersionUID = 1L;

}
