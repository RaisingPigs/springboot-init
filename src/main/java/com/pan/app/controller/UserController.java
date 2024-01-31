package com.pan.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pan.app.annotation.AuthCheck;
import com.pan.app.common.req.DeleteRequest;
import com.pan.app.common.resp.BaseResponse;
import com.pan.app.common.resp.ResultCode;
import com.pan.app.common.resp.ResultUtils;
import com.pan.app.constant.PageConstant;
import com.pan.app.exception.BusinessException;
import com.pan.app.model.dto.user.UserDTO;
import com.pan.app.model.entity.User;
import com.pan.app.model.req.user.*;
import com.pan.app.model.vo.user.UserVO;
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
        @RequestBody UserRegistReq userRegistReq) {
        if (userRegistReq == null) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        String account = userRegistReq.getAccount();
        String password = userRegistReq.getPassword();
        String checkPassword = userRegistReq.getCheckPassword();
        if (StringUtils.isAnyBlank(account, password, checkPassword)) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        long id = userService.userRegist(account, password, checkPassword);
        return ResultUtils.success(id);
    }

    @PostMapping("/login")
    public BaseResponse<UserVO> userLogin(
        @RequestBody UserLoginReq userLoginReq,
        HttpServletRequest request) {
        if (userLoginReq == null) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }
        String account = userLoginReq.getAccount();
        String password = userLoginReq.getPassword();
        if (StringUtils.isAnyBlank(account, password)) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        UserVO userVo = userService.userLogin(account, password, request);

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
    public BaseResponse<UserVO> getLoginUser() {
        UserDTO userDTO = UserHolder.getUser();
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDTO, userVO);

        return ResultUtils.success(userVO);
    }

    //region 增删改查
    @AuthCheck
    @PostMapping("/add")
    public BaseResponse<Long> addUser(
        @RequestBody UserAddReq userAddReq) {
        if (userAddReq == null) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        User user = new User();
        BeanUtils.copyProperties(userAddReq, user);

        userService.validUser(user, true);
        boolean save = userService.save(user);
        if (!save) {
            throw new BusinessException(ResultCode.SAVE_ERR);
        }

        return ResultUtils.success(user.getId());
    }

    @AuthCheck
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

    @AuthCheck
    @PutMapping("/update")
    public BaseResponse<Boolean> updateUser(
        @RequestBody UserUpdateReq userUpdateReq) {
        if (userUpdateReq == null) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        User user = new User();
        BeanUtils.copyProperties(userUpdateReq, user);
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

    @AuthCheck
    @GetMapping("/get/{id}")
    public BaseResponse<User> getUserById(@PathVariable("id") long id) {
        if (id <= 0) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        User user = userService.getById(id);
        return ResultUtils.success(user);
    }

    @AuthCheck
    @GetMapping("/list")
    public BaseResponse<List<User>> listUser(
        UserQueryReq userQueryReq) {
        User userQuery = new User();
        if (userQueryReq != null) {
            BeanUtils.copyProperties(userQueryReq, userQuery);
        }

        QueryWrapper<User> wrapper = new QueryWrapper<>(userQuery);
        List<User> userList = userService.list(wrapper);
        return ResultUtils.success(userList);
    }

    @AuthCheck
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

        User userQuery = new User();
        BeanUtils.copyProperties(userQueryReq, userQuery);

        long pagenum = userQueryReq.getPagenum();
        long pagesize = userQueryReq.getPagesize();
        String sortField = userQueryReq.getSortField();
        boolean sortOrder = userQueryReq.isSortOrder();
        String username = userQuery.getUsername();

        QueryWrapper<User> wrapper = new QueryWrapper<>(userQuery);
        /*使用userName字段做模糊查询*/
        wrapper.like(StringUtils.isNotBlank(username), "username", username);
        wrapper.orderBy(StringUtils.isNotBlank(sortField), sortOrder, sortField);
        Page<User> page = userService.page(new Page<>(pagenum, pagesize), wrapper);

        return ResultUtils.success(page);
    }
    //endregion
}
