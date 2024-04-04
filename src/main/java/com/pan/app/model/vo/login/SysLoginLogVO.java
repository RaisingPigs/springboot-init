package com.pan.app.model.vo.login;

import com.pan.app.model.enums.login.State;
import com.pan.app.model.enums.login.Type;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-04-04 19:32
 **/
@Data
public class SysLoginLogVO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键id
     */
    private Long id;
    
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户账号
     */
    private String username;
    /**
     * 登录方式
     */
    private Type loginType;
    /**
     * 登录IP地址
     */
    private String loginIp;
    /**
     * 浏览器类型
     */
    private String browser;
    /**
     * 操作系统
     */
    private String os;
    /**
     * 登录状态（1成功 0失败）
     */
    private State status;
    /**
     * 提示消息
     */
    private String msg;
    
    private LocalDateTime loginTime;
}
