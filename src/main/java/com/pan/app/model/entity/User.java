package com.pan.app.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pan.app.common.entity.BaseEntity;
import com.pan.app.model.enums.user.GenderEnum;
import com.pan.app.model.enums.user.RoleEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户
 * @TableName user
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "user")
@Data
public class User extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户昵称
     */
    private String username;
    /**
     * 账号
     */
    @TableField("account")
    private String account;
    /**
     * 用户头像
     */
    @TableField("avatar")
    private String avatar;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 用户角色：user / admin
     */
    @TableField("role")
    private Integer role;
    /**
     * 密码
     */
    @TableField("password")
    private String password;

    public User() {
    }

    public User(String username, String account, String password) {
        this.username = username;
        this.account = account;
        this.password = password;
        this.avatar = "";
        this.gender = GenderEnum.MALE.getCode();
        this.role = RoleEnum.USER.getCode();
    }
}