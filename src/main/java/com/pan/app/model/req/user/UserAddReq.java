package com.pan.app.model.req.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2023-02-21 16:31
 **/
@Data
public class UserAddReq implements Serializable {
    /**
     * 用户昵称
     */
    private String username;

    /**
     * 账号
     */
    private String account;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 用户角色：user / admin
     */
    private Integer role;

    /**
     * 密码
     */
    private String password;

    private static final long serialVersionUID = 1L;
}
