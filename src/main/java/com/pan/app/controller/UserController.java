package com.pan.app.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pan.app.common.req.DeleteRequest;
import com.pan.app.common.resp.BaseResponse;
import com.pan.app.common.resp.ResultCode;
import com.pan.app.common.resp.ResultUtils;
import com.pan.app.constant.PageConstant;
import com.pan.app.exception.BusinessException;
import com.pan.app.model.converter.user.UserConverter;
import com.pan.app.model.converter.user.UserVOConverter;
import com.pan.app.model.dto.user.UserDTO;
import com.pan.app.model.entity.User;
import com.pan.app.model.req.user.*;
import com.pan.app.model.vo.user.UserVO;
import com.pan.app.service.UserService;
import com.pan.app.utils.AuthUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2023-02-21 16:48
 **/
@Validated
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/regist")
    public BaseResponse<Long> userRegist(
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

        long id = userService.userRegist(username, password, checkPassword);
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

        String token = userService.userLogin(username, password);

        return ResultUtils.success(token);
    }

    @SaCheckLogin
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout() {
        userService.userLogout();
        return ResultUtils.success(true);
    }

    @SaCheckLogin
    @GetMapping("/info")
    public BaseResponse<UserVO> getLoginUser() {
        UserDTO userDTO = AuthUtils.getLoginUser();
        UserVO userVO = UserVOConverter.INSTANCE.toUserVO(userDTO);

        return ResultUtils.success(userVO);
    }

    //region 增删改查
    @SaCheckRole("admin")
    @PostMapping("/add")
    public BaseResponse<Long> addUser(
        @RequestBody UserAddReq userAddReq) {
        if (userAddReq == null) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        User user = UserConverter.INSTANCE.toUser(userAddReq);

        userService.validUser(user, true);
        boolean save = userService.saveUser(user);
        if (!save) {
            throw new BusinessException(ResultCode.SAVE_ERR);
        }

        return ResultUtils.success(user.getId());
    }

    @SaCheckRole("admin")
    @DeleteMapping("/delete")
    public BaseResponse<Boolean> deleteUser(
        @RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        long id = deleteRequest.getId();
        User oldUser = userService.getById(id);
        if (oldUser == null) {
            throw new BusinessException(ResultCode.NULL_ERR);
        }

        boolean b = userService.removeById(id);
        if (!b) {
            throw new BusinessException(ResultCode.DELETE_ERR);
        }

        return ResultUtils.success(true);
    }

    @SaCheckRole("admin")
    @PutMapping("/update")
    public BaseResponse<Boolean> updateUser(
        @RequestBody UserUpdateReq userUpdateReq) {
        if (userUpdateReq == null) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        User user = UserConverter.INSTANCE.toUser(userUpdateReq);
        userService.validUser(user, false);

        User oldUser = userService.getById(user.getId());
        if (oldUser == null) {
            throw new BusinessException(ResultCode.NULL_ERR);
        }

        boolean b = userService.updateById(user);
        if (!b) {
            throw new BusinessException(ResultCode.UPDATE_ERR);
        }

        return ResultUtils.success(true);
    }

    @SaCheckRole("admin")
    @GetMapping("/get/{id}")
    public BaseResponse<User> getUserById(
        @PathVariable("id")
        @NotNull(message = "参数异常")
        @Min(value = 1, message = "id不正确")
        Long id) {

        User user = userService.getById(id);
        return ResultUtils.success(user);
    }

    @SaCheckRole("admin")
    @GetMapping("/list")
    public BaseResponse<List<User>> listUser(
        UserQueryReq userQueryReq) {
        User user = null;
        if (userQueryReq != null) {
            user = UserConverter.INSTANCE.toUser(userQueryReq);
        }

        QueryWrapper<User> wrapper = new QueryWrapper<>(user);
        List<User> userList = userService.list(wrapper);
        return ResultUtils.success(userList);
    }

    @SaCheckRole("admin")
    @GetMapping("/list/page")
    public BaseResponse<Page<User>> listUserByPage(
        UserQueryReq userQueryReq) {
        if (userQueryReq == null) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        /*限制爬虫*/
        if (userQueryReq.getPagesize() > PageConstant.MAX_PAGE_SIZE) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        User user = UserConverter.INSTANCE.toUser(userQueryReq);

        long pagenum = userQueryReq.getPagenum();
        long pagesize = userQueryReq.getPagesize();
        String sortField = userQueryReq.getSortField();
        boolean sortOrder = userQueryReq.isSortOrder();
        String username = user.getName();

        QueryWrapper<User> wrapper = new QueryWrapper<>(user);
        /*使用userName字段做模糊查询*/
        wrapper.like(StringUtils.isNotBlank(username), "username", username);
        wrapper.orderBy(StringUtils.isNotBlank(sortField), sortOrder, sortField);
        Page<User> page = userService.page(new Page<>(pagenum, pagesize), wrapper);

        return ResultUtils.success(page);
    }
    //endregion
}
