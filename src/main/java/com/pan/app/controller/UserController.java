package com.pan.app.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pan.app.annotation.OperationLog;
import com.pan.app.common.resp.BaseResponse;
import com.pan.app.common.resp.ResultCode;
import com.pan.app.common.resp.ResultUtils;
import com.pan.app.constant.PageConstant;
import com.pan.app.exception.BusinessException;
import com.pan.app.model.converter.user.UserConverter;
import com.pan.app.model.converter.user.UserVOConverter;
import com.pan.app.model.entity.User;
import com.pan.app.model.enums.operation.BusinessType;
import com.pan.app.model.enums.operation.Method;
import com.pan.app.model.req.user.UserAddReq;
import com.pan.app.model.req.user.UserQueryReq;
import com.pan.app.model.req.user.UserUpdateReq;
import com.pan.app.model.vo.user.UserVO;
import com.pan.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2023-02-21 16:48
 **/
@Validated
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@SaCheckRole("admin")
public class UserController {
    private final UserService userService;

    //region 增删改查
    @OperationLog(businessType = BusinessType.INSERT, reqMethod = Method.POST, reqModule = "用户模块")
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

    @OperationLog(businessType = BusinessType.DELETE, reqMethod = Method.DELETE, reqModule = "用户模块")
    @DeleteMapping("/delete/{id}")
    public BaseResponse<Void> deleteUser(@PathVariable("id") Long id) {
        if (Objects.isNull(id) || id <= 0) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        User oldUser = userService.getById(id);
        if (oldUser == null) {
            throw new BusinessException(ResultCode.NULL_ERR);
        }

        boolean b = userService.removeById(id);
        if (!b) {
            throw new BusinessException(ResultCode.DELETE_ERR);
        }

        return ResultUtils.success();
    }

    @OperationLog(businessType = BusinessType.UPDATE, reqMethod = Method.PUT, reqModule = "用户模块")
    @PutMapping("/update")
    public BaseResponse<Void> updateUser(
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

        return ResultUtils.success();
    }

    @GetMapping("/get/{id}")
    public BaseResponse<UserVO> getUserById(
        @PathVariable("id")
        @NotNull(message = "参数异常")
        @Min(value = 1, message = "id不正确")
        Long id) {

        User user = userService.getById(id);
        UserVO userVO = UserVOConverter.INSTANCE.toUserVO(user);
        return ResultUtils.success(userVO);
    }

    @PostMapping("/list")
    public BaseResponse<List<UserVO>> listUser(
        @RequestBody UserQueryReq userQueryReq) {
        User user = null;
        if (userQueryReq != null) {
            user = UserConverter.INSTANCE.toUser(userQueryReq);
        }

        QueryWrapper<User> wrapper = new QueryWrapper<>(user);
        List<User> userList = userService.list(wrapper);

        List<UserVO> userVOList = userList.stream().map(UserVOConverter.INSTANCE::toUserVO).collect(Collectors.toList());
        return ResultUtils.success(userVOList);
    }

    @PostMapping("/list/page")
    public BaseResponse<IPage<UserVO>> listUserByPage(
        @RequestBody UserQueryReq userQueryReq) {
        if (userQueryReq == null) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        /*限制爬虫*/
        if (userQueryReq.getPageSize() > PageConstant.MAX_PAGE_SIZE) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        User user = UserConverter.INSTANCE.toUser(userQueryReq);

        long pageNum = userQueryReq.getPageNum();
        long pageSize = userQueryReq.getPageSize();
        String sortField = userQueryReq.getSortField();
        boolean sortOrder = userQueryReq.isSortOrder();
        String name = user.getName();
        String username = user.getUsername();
        user.setName(null);
        user.setUsername(null);

        QueryWrapper<User> wrapper = new QueryWrapper<>(user);
        /*使用userName字段做模糊查询*/
        wrapper.like(StringUtils.isNotBlank(name), "name", name);
        wrapper.like(StringUtils.isNotBlank(username), "username", username);
        wrapper.orderBy(StringUtils.isNotBlank(sortField), sortOrder, sortField);

        IPage<User> userPage = userService.page(new Page<>(pageNum, pageSize), wrapper);
        IPage<UserVO> userVOPage = userPage.convert(UserVOConverter.INSTANCE::toUserVO);

        return ResultUtils.success(userVOPage);
    }
    //endregion
}
