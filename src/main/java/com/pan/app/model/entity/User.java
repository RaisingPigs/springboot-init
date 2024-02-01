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
    @TableField("name")
    private String name;
    /**
     * 账号
     */
    @TableField("username")
    private String username;
    /**
     * 密码
     */
    @TableField("password")
    private String password;
    /**
     * 用户头像
     */
    @TableField("avatar")
    private String avatar;
    /**
     * 性别
     */
    @TableField("gender")
    private GenderEnum gender;
    /**
     * 用户角色：user / admin
     */
    @TableField("role")
    private RoleEnum role;

    public User() {
    }

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.avatar = "";
        this.gender = GenderEnum.MALE;
        this.role = RoleEnum.USER;
    }
}