package com.pan.app.model.vo.user;

import com.pan.app.model.enums.user.GenderEnum;
import com.pan.app.model.enums.user.RoleEnum;
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
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
