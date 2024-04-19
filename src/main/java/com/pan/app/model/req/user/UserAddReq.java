package com.pan.app.model.req.user;

import com.pan.app.model.enums.user.GenderEnum;
import com.pan.app.model.enums.user.RoleEnum;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2023-02-21 16:31
 **/
@Data
public class UserAddReq implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户昵称
     */
    @NotBlank(message = "用户昵称不能为空")
    private String name;
    /**
     * 账号
     */
    @NotBlank(message = "用户名不能为空")
    @Length(min = 5, max = 16, message = "用户名必须为5~16位")
    private String username;
    /**
     * 用户头像
     */
    @NotBlank(message = "用户头像不能为空")
    private String avatar;
    /**
     * 性别
     */
    @NotNull(message = "用户性别不能为空")
    private GenderEnum gender;
    /**
     * 用户角色：user / admin
     */
    @NotNull(message = "用户角色不能为空")
    private RoleEnum role;
}
