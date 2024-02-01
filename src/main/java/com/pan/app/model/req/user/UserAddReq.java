package com.pan.app.model.req.user;

import com.pan.app.model.enums.user.GenderEnum;
import com.pan.app.model.enums.user.RoleEnum;
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
    private String name;

    /**
     * 账号
     */
    private String username;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 性别
     */
    private GenderEnum gender;

    /**
     * 用户角色：user / admin
     */
    private RoleEnum role;

    /**
     * 密码
     */
    private String password;

    private static final long serialVersionUID = 1L;
}
