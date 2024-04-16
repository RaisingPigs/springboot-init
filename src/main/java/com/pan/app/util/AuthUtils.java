package com.pan.app.util;

import cn.dev33.satoken.stp.StpUtil;
import com.pan.app.common.resp.BizCode;
import com.pan.app.constant.UserConstant;
import com.pan.app.exception.BizException;
import com.pan.app.model.dto.user.UserDTO;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-02-02 23:18
 **/

public class AuthUtils {
    private AuthUtils() {
    }

    public static Long getLoginUserId() {
        return getLoginUser().getId();
    }

    public static UserDTO getLoginUser() {
        UserDTO userDTO = StpUtil.getSession().getModel(UserConstant.USER_LOGIN_STATE, UserDTO.class);

        return Optional.ofNullable(userDTO)
            .orElseThrow(() -> new BizException(BizCode.NO_AUTH));
    }

    public static void setLoginUser(UserDTO userDTO) {
        if (Objects.isNull(userDTO)) {
            throw new BizException(BizCode.SYSTEM_ERR, "userDTO为空");
        }

        StpUtil.getSession().set(UserConstant.USER_LOGIN_STATE, userDTO);
    }

    public static String encryptPassword(String password) {
        return DigestUtils.md5DigestAsHex((UserConstant.SALT + password).getBytes(StandardCharsets.UTF_8));
    }
}

