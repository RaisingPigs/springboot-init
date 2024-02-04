package com.pan.app.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pan.app.common.resp.ResultCode;
import com.pan.app.constant.UserConstant;
import com.pan.app.exception.BusinessException;
import com.pan.app.mapper.UserMapper;
import com.pan.app.model.entity.User;
import com.pan.app.model.enums.user.GenderEnum;
import com.pan.app.model.enums.user.RoleEnum;
import com.pan.app.service.UserService;
import com.pan.app.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
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
    public void validUser(User user, boolean add) {
        if (user == null) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        String username = user.getUsername();
        String name = user.getName();
        String avatar = user.getAvatar();
        GenderEnum gender = user.getGender();
        RoleEnum role = user.getRole();

        /*创建时，所有参数必须非空*/
        if (add) {
            if (StringUtils.isAnyBlank(username, name, avatar)
                || ObjectUtil.hasNull(gender, role)) {
                throw new BusinessException(ResultCode.PARAMS_ERR);
            }
        }

        if (StringUtils.isNotBlank(name) && name.length() > 16) {
            throw new BusinessException(ResultCode.PARAMS_ERR, "用户名过长");
        }
        
        if (ObjectUtil.hasNull(gender, role)) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }
    }

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




