package com.pan.app.utils;

import cn.dev33.satoken.stp.StpUtil;
import com.pan.app.common.resp.ResultCode;
import com.pan.app.constant.UserConstant;
import com.pan.app.exception.BusinessException;
import com.pan.app.model.dto.user.UserDTO;

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

    public static UserDTO getLoginUser() {
        UserDTO userDTO = StpUtil.getSession().getModel(UserConstant.USER_LOGIN_STATE, UserDTO.class);

        return Optional.ofNullable(userDTO)
            .orElseThrow(() -> new BusinessException(ResultCode.NO_AUTH));
    }

    public static void setLoginUser(UserDTO userDTO) {
        if (Objects.isNull(userDTO)) {
            throw new BusinessException(ResultCode.SYSTEM_ERR, "userDTO为空");
        }
        
        StpUtil.getSession().set(UserConstant.USER_LOGIN_STATE, userDTO);
    }
}
