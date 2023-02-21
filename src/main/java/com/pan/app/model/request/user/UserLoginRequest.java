package com.pan.app.model.request.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2023-02-21 16:33
 **/
@Data
public class UserLoginRequest implements Serializable {
    /**
     * 账号
     */
    private String userAccount;
    
    /**
     * 密码
     */
    private String userPassword;
    
    private static final long serialVersionUID = 1L;
}
