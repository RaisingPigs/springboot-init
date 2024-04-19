package com.pan.app.model.req.user;

import com.pan.app.common.param.ParamChecker;
import com.pan.app.common.resp.BizCode;
import com.pan.app.exception.BizException;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2023-02-21 16:34
 **/
@Data
public class UserRegistReq implements Serializable, ParamChecker {
    private static final long serialVersionUID = 1L;
    @NotBlank(message = "用户名不能为空")
    @Length(min = 5, max = 16, message = "用户名必须为5~16位")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(min = 5, max = 16, message = "密码必须为5~16位")
    private String password;

    @NotBlank(message = "密码二次输入不能为空")
    @Length(min = 5, max = 16, message = "密码二次输入必须为5~16位")
    private String checkPassword;

    @Override
    public void checkParam() {
        if (!Objects.equals(checkPassword, password)) {
            throw new BizException(BizCode.PARAMS_ERR, "两次输入密码不一致");
        }
    }
}
