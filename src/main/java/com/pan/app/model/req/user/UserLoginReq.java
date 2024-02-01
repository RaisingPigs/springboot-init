package com.pan.app.model.req.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2023-02-21 16:33
 **/
@Data
public class UserLoginReq implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 账号
     */
    @NotBlank(message = "用户名不能为空")
    @Length(min = 5, max = 16, message = "用户名必须为5~16位")
    private String username;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Length(min = 5, max = 16, message = "密码必须为5~16位")
    private String password;
}
