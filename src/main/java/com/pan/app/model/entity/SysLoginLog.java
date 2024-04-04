package com.pan.app.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pan.app.common.entity.BaseEntity;
import com.pan.app.model.enums.login.State;
import com.pan.app.model.enums.login.Type;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 系统访问记录
 * @TableName sys_login_info
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_login_log")
public class SysLoginLog extends BaseEntity {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Long userId;
    /**
     * 用户账号
     */
    @TableField(value = "username")
    private String username;
    /**
     * 登录方式
     */
    @TableField(value = "login_type")
    private Type loginType;
    /**
     * 登录IP地址
     */
    @TableField(value = "login_ip")
    private String loginIp;
    /**
     * 浏览器类型
     */
    @TableField(value = "browser")
    private String browser;
    /**
     * 操作系统
     */
    @TableField(value = "os")
    private String os;
    /**
     * 登录状态（1成功 0失败）
     */
    @TableField(value = "status")
    private State status;
    /**
     * 提示消息
     */
    @TableField(value = "msg")
    private String msg;

    public SysLoginLog(Long userId, String username, Type loginType, String loginIp, String browser, String os, State status, String msg) {
        this.userId = userId;
        this.username = username;
        this.loginType = loginType;
        this.loginIp = loginIp;
        this.browser = browser;
        this.os = os;
        this.status = status;
        this.msg = msg;
        setCreatorId(userId);
        setUpdaterId(userId);
    }
}