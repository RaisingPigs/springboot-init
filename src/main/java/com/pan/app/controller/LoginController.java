package com.pan.app.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.pan.app.annotation.LoginLog;
import com.pan.app.model.converter.user.UserVOConverter;
import com.pan.app.model.dto.user.UserDTO;
import com.pan.app.model.enums.login.Type;
import com.pan.app.model.req.user.UserLoginReq;
import com.pan.app.model.req.user.UserRegistReq;
import com.pan.app.model.vo.user.UserVO;
import com.pan.app.service.LoginService;
import com.pan.app.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-02-04 05:18
 **/
@RestController
@RequestMapping("/sys")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody @Validated UserRegistReq userRegistReq) {

        return loginService.userRegister(userRegistReq);
    }

    @LoginLog(loginType = Type.DEFAULT, username = "#userLoginReq.username")
    @PostMapping("/login")
    public Map<String, String> userLogin(@RequestBody @Validated UserLoginReq userLoginReq) {
        String token = loginService.userLogin(userLoginReq);

        return Collections.singletonMap("token", token);
    }

    @SaCheckLogin
    @PostMapping("/logout")
    public void userLogout() {
        loginService.userLogout();
    }

    @SaCheckLogin
    @GetMapping("/user")
    public UserVO getLoginUser() {
        UserDTO userDTO = AuthUtils.getLoginUser();
        return UserVOConverter.INSTANCE.toUserVO(userDTO);
    }
}
