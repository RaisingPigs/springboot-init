package com.pan.app.model.vo.user;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2023-02-21 16:35
 **/
@Data
public class UserVO implements Serializable {
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
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
