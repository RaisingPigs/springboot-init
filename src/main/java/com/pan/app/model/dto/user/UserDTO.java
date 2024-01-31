package com.pan.app.model.dto.user;

import com.pan.app.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2023-02-26 23:41
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class UserDTO extends BaseEntity {
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
    private Integer role;
}
