package com.pan.app.model.req.user;

import com.pan.app.common.req.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2023-02-21 16:22
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryReq extends PageRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;
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

    private Long creatorId;

    private LocalDateTime createTime;
    
    private LocalDateTime updateTime;

}
