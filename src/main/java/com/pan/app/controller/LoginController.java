package com.pan.app.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.pan.app.common.resp.BaseResponse;
import com.pan.app.common.resp.ResultCode;
import com.pan.app.common.resp.ResultUtils;
import com.pan.app.exception.BusinessException;
import com.pan.app.model.converter.user.UserVOConverter;
import com.pan.app.model.dto.user.UserDTO;
import com.pan.app.model.req.user.UserLoginReq;
import com.pan.app.model.req.user.UserRegistReq;
import com.pan.app.model.vo.user.UserVO;
import com.pan.app.service.LoginService;
import com.pan.app.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-02-04 05:18
 **/
@Validated
@RestController
@RequestMapping("/sys")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(
        @RequestBody @Validated UserRegistReq userRegistReq) {
        if (userRegistReq == null) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        String username = userRegistReq.getUsername();
        String password = userRegistReq.getPassword();
        String checkPassword = userRegistReq.getCheckPassword();
        if (StringUtils.isAnyBlank(username, password, checkPassword)) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        long id = loginService.userRegister(username, password, checkPassword);
        return ResultUtils.success(id);
    }

    @PostMapping("/login")
    public BaseResponse<String> userLogin(
        @RequestBody @Validated UserLoginReq userLoginReq) {
        if (userLoginReq == null) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }
        String username = userLoginReq.getUsername();
        String password = userLoginReq.getPassword();
        if (StringUtils.isAnyBlank(username, password)) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        String token = loginService.userLogin(username, password);

        return ResultUtils.success(token);
    }

    @SaCheckLogin
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout() {
        loginService.userLogout();
        return ResultUtils.success(true);
    }

    @SaCheckLogin
    @GetMapping("/user")
    public BaseResponse<UserVO> getLoginUser() {
        UserDTO userDTO = AuthUtils.getLoginUser();
        UserVO userVO = UserVOConverter.INSTANCE.toUserVO(userDTO);

        return ResultUtils.success(userVO);
    }
}
