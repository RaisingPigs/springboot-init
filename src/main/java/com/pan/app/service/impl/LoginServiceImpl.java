package com.pan.app.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import com.pan.app.common.resp.BizCode;
import com.pan.app.constant.UserConstant;
import com.pan.app.exception.BizException;
import com.pan.app.model.converter.user.UserDTOConverter;
import com.pan.app.model.dto.user.UserDTO;
import com.pan.app.model.entity.User;
import com.pan.app.model.req.user.UserLoginReq;
import com.pan.app.model.req.user.UserRegistReq;
import com.pan.app.service.LoginService;
import com.pan.app.service.UserService;
import com.pan.app.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-02-04 05:23
 **/
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final UserService userService;

    @Override
    public long userRegister(UserRegistReq userRegistReq) {
        String username = userRegistReq.getUsername();
        String password = userRegistReq.getPassword();

        synchronized (username.intern()) {
            long count = userService.lambdaQuery().eq(User::getUsername, username).count();
            if (count > 0) {
                throw new BizException(BizCode.PARAMS_ERR, "该用户名已被使用");
            }

            /*加密*/
            String encryptPassword = AuthUtils.encryptPassword(password);
            String defaultUsername = UserConstant.DEFAULT_USERNAME_PREFIX + RandomUtil.randomString(10);
            User user = new User(defaultUsername, username, encryptPassword);
            boolean save = userService.save(user);
            if (!save) {
                throw new BizException(BizCode.PARAMS_ERR);
            }

            return user.getId();
        }

    }

    @Override
    public String userLogin(UserLoginReq userLoginReq) {
        String encryptPassword = AuthUtils.encryptPassword(userLoginReq.getPassword());
        User user = userService.lambdaQuery()
            .eq(User::getUsername, userLoginReq.getUsername())
            .eq(User::getPassword, encryptPassword)
            .one();
        if (user == null) {
            throw new BizException(BizCode.NULL_ERR, "用户名或密码错误");
        }

        UserDTO userDTO = UserDTOConverter.INSTANCE.toUserDTO(user);
        StpUtil.login(user.getId());
        AuthUtils.setLoginUser(userDTO);

        return StpUtil.getTokenValue();
    }

    @Override
    public void userLogout() {
        StpUtil.logout();
    }
}
