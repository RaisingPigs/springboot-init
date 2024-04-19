package com.pan.app.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pan.app.annotation.OperationLog;
import com.pan.app.common.resp.BizCode;
import com.pan.app.exception.BizException;
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
import java.util.Objects;

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
    public Long addUser(@RequestBody @Validated UserAddReq userAddReq) {
        User user = UserConverter.INSTANCE.toUser(userAddReq);

        boolean save = userService.saveUser(user);
        if (!save) {
            throw new BizException(BizCode.SYSTEM_ERR);
        }

        return user.getId();
    }

    @OperationLog(businessType = BusinessType.DELETE, reqMethod = Method.DELETE, reqModule = "用户模块")
    @DeleteMapping("/delete/{id}")
    public void deleteUser(
        @PathVariable("id")
        @NotNull(message = "参数异常")
        @Min(value = 1, message = "id不正确")
        Long id
    ) {
        if (Objects.isNull(id) || id <= 0) {
            throw new BizException(BizCode.PARAMS_ERR);
        }

        User oldUser = userService.getById(id);
        if (oldUser == null) {
            throw new BizException(BizCode.NULL_ERR);
        }

        boolean b = userService.removeById(id);
        if (!b) {
            throw new BizException(BizCode.SYSTEM_ERR);
        }
    }

    @OperationLog(businessType = BusinessType.UPDATE, reqMethod = Method.PUT, reqModule = "用户模块")
    @PutMapping("/update")
    public void updateUser(
        @RequestBody @Validated UserUpdateReq userUpdateReq) {
        if (userUpdateReq == null) {
            throw new BizException(BizCode.PARAMS_ERR);
        }

        User user = UserConverter.INSTANCE.toUser(userUpdateReq);

        User oldUser = userService.getById(user.getId());
        if (oldUser == null) {
            throw new BizException(BizCode.NULL_ERR);
        }

        boolean b = userService.updateById(user);
        if (!b) {
            throw new BizException(BizCode.SYSTEM_ERR);
        }
    }

    @GetMapping("/get/{id}")
    public UserVO getUserById(
        @PathVariable("id")
        @NotNull(message = "参数异常")
        @Min(value = 1, message = "id不正确")
        Long id
    ) {

        User user = userService.getById(id);
        return UserVOConverter.INSTANCE.toUserVO(user);
    }

    @PostMapping("/list/page")
    public IPage<UserVO> listUserByPage(
        @RequestBody UserQueryReq userQueryReq) {
        long pageNum = userQueryReq.getPageNum();
        long pageSize = userQueryReq.getPageSize();
        String sortField = userQueryReq.getSortField();
        boolean sortOrder = userQueryReq.isSortOrder();
        String name = userQueryReq.getName();
        String username = userQueryReq.getUsername();

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderBy(StringUtils.isNotBlank(sortField), sortOrder, sortField);

        LambdaQueryWrapper<User> lambdaQueryWrapper = wrapper.lambda()
            .like(StringUtils.isNotBlank(name), User::getName, name)
            .like(StringUtils.isNotBlank(username), User::getUsername, username);

        IPage<User> userPage = userService.page(new Page<>(pageNum, pageSize), lambdaQueryWrapper);
        return userPage.convert(UserVOConverter.INSTANCE::toUserVO);
    }
    //endregion
}
