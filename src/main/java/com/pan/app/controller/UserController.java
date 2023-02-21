package com.pan.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pan.app.annotation.AuthCheck;
import com.pan.app.common.BaseResponse;
import com.pan.app.common.DeleteRequest;
import com.pan.app.common.ResultCode;
import com.pan.app.common.ResultUtils;
import com.pan.app.constant.UserConstant;
import com.pan.app.exception.BusinessException;
import com.pan.app.model.entity.User;
import com.pan.app.model.request.user.*;
import com.pan.app.model.vo.UserVo;
import com.pan.app.service.UserService;
import com.pan.app.utils.UserHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2023-02-21 16:48
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    
    @PostMapping("/regist")
    public BaseResponse<Long> userRegist(
            @RequestBody UserRegistRequest userRegistRequest) {
        if (userRegistRequest == null) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        String userAccount = userRegistRequest.getUserAccount();
        String userPassword = userRegistRequest.getUserPassword();
        String checkPassword = userRegistRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        long id = userService.userRegist(userAccount, userPassword, checkPassword);
        return ResultUtils.success(id);
    }

    @PostMapping("/login")
    public BaseResponse<UserVo> userLogin(
            @RequestBody UserLoginRequest userLoginRequest,
            HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        UserVo userVo = userService.userLogin(userAccount, userPassword, request);
        
        return ResultUtils.success(userVo);
    }

    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        boolean res = userService.userLogout(request);
        return ResultUtils.success(res);
    }

    @GetMapping("/get/login")
    public BaseResponse<UserVo> getLoginUser(HttpServletRequest request) {
        UserVo userVo = UserHolder.getUser();
        return ResultUtils.success(userVo);
    }

    //region 增删改查
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @PostMapping("/add")
    public BaseResponse<Long> addUser(
            @RequestBody UserAddRequest userAddRequest) {
        if (userAddRequest == null) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        User user = new User();
        BeanUtils.copyProperties(userAddRequest, user);

        userService.validUser(user, true);
        boolean save = userService.save(user);
        if (!save) {
            throw new BusinessException(ResultCode.SAVE_ERR);
        }

        return ResultUtils.success(user.getId());
    }

    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
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

    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @PutMapping("/update")
    public BaseResponse<Boolean> updateUser(
            @RequestBody UserUpdateRequest userUpdateRequest) {
        if (userUpdateRequest == null) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        User user = new User();
        BeanUtils.copyProperties(userUpdateRequest, user);
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

    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @GetMapping("/get/{id}")
    public BaseResponse<User> getUserById(@PathVariable("id") long id) {
        if (id <= 0) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        User user = userService.getById(id);
        return ResultUtils.success(user);
    }

    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @GetMapping("/list")
    public BaseResponse<List<User>> listUser(
            UserQueryRequest userQueryRequest) {
        User userQuery = new User();
        if (userQueryRequest != null) {
            BeanUtils.copyProperties(userQueryRequest, userQuery);
        }

        QueryWrapper<User> wrapper = new QueryWrapper<>(userQuery);
        List<User> userList = userService.list(wrapper);
        return ResultUtils.success(userList);
    }

    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @GetMapping("/list/page")
    public BaseResponse<Page<User>> listUserByPage(
            UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        /*限制爬虫*/
        if (userQueryRequest.getPagesize() > 50) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        User userQuery = new User();
        BeanUtils.copyProperties(userQueryRequest, userQuery);

        long pagenum = userQueryRequest.getPagenum();
        long pagesize = userQueryRequest.getPagesize();
        String sortField = userQueryRequest.getSortField();
        boolean sortOrder = userQueryRequest.isSortOrder();
        String userName = userQuery.getUserName();

        QueryWrapper<User> wrapper = new QueryWrapper<>(userQuery);
        /*使用userName字段做模糊查询*/
        wrapper.like(StringUtils.isNotBlank(userName), "userName", userName);
        wrapper.orderBy(StringUtils.isNotBlank(sortField), sortOrder, sortField);
        Page<User> page = userService.page(new Page<>(pagenum, pagesize), wrapper);

        return ResultUtils.success(page);
    }
    //endregion
}
