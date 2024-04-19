package com.pan.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pan.app.constant.UserConstant;
import com.pan.app.mapper.UserMapper;
import com.pan.app.model.entity.User;
import com.pan.app.model.enums.user.RoleEnum;
import com.pan.app.service.UserService;
import com.pan.app.util.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Mr.Pan
 * @description 针对表【mp_user】的数据库操作Service实现
 * @createDate 2023-02-21 16:25:21
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {
    @Override
    public boolean isAdmin() {
        RoleEnum userRole = AuthUtils.getLoginUser().getRole();
        return Objects.equals(userRole, RoleEnum.ADMIN);
    }

    @Override
    public boolean saveUser(User user) {
        String encryptPassword = AuthUtils.encryptPassword(UserConstant.DEFAULT_PASSWORD);
        user.setPassword(encryptPassword);

        return save(user);
    }
}




