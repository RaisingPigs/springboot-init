package com.pan.app.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pan.app.common.resp.ResultCode;
import com.pan.app.constant.RedisConstant;
import com.pan.app.exception.BusinessException;
import com.pan.app.mapper.UserMapper;
import com.pan.app.model.dto.user.UserDTO;
import com.pan.app.model.entity.User;
import com.pan.app.model.enums.user.GenderEnum;
import com.pan.app.model.enums.user.RoleEnum;
import com.pan.app.model.vo.user.UserVO;
import com.pan.app.service.UserService;
import com.pan.app.utils.UserHolder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * @author Mr.Pan
 * @description 针对表【mp_user】的数据库操作Service实现
 * @createDate 2023-02-21 16:25:21
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {
    /*加盐*/
    private static final String SALT = "pan";
    private static final String DEFAULT_USERNAME_PREFIX = "默认用户名";
    private final UserMapper userMapper;

    @Override
    public void validUser(User user, boolean add) {
        if (user == null) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        String account = user.getAccount();
        String username = user.getUsername();
        String password = user.getPassword();
        String avatar = user.getAvatar();
        GenderEnum gender = GenderEnum.of(user.getGender());
        RoleEnum role = RoleEnum.of(user.getRole());

        /*创建时，所有参数必须非空*/
        if (add) {
            if (StringUtils.isAnyBlank(account, username, password, avatar)) {
                throw new BusinessException(ResultCode.PARAMS_ERR);
            }
        }

        if (StringUtils.isNotBlank(username) && username.length() > 16) {
            throw new BusinessException(ResultCode.PARAMS_ERR, "用户名过长");
        }

        if (StringUtils.isNotBlank(password) && password.length() > 16) {
            throw new BusinessException(ResultCode.PARAMS_ERR, "密码过长");
        }
    }

    @Override
    public long userRegist(String account, String password, String checkPassword) {
        if (StringUtils.isAnyBlank(account, password, checkPassword)) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        if (account.length() < 5
            || account.length() > 18
            || password.length() < 5
            || password.length() > 18) {
            throw new BusinessException(ResultCode.PARAMS_ERR, "用户名密码长度不符合规范");
        }

        if (!checkPassword.equals(password)) {
            throw new BusinessException(ResultCode.PARAMS_ERR, "两次输入密码不一致");
        }

        synchronized (account.intern()) {
            int count = lambdaQuery().eq(User::getAccount, account).count();
            if (count > 0) {
                throw new BusinessException(ResultCode.SAVE_ERR, "该用户名已被使用");
            }

            /*加密*/
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes(StandardCharsets.UTF_8));
            String defaultUsername = DEFAULT_USERNAME_PREFIX + RandomUtil.randomString(10);
            User user = new User(defaultUsername, account, encryptPassword);
            boolean save = save(user);
            if (!save) {
                throw new BusinessException(ResultCode.SAVE_ERR);
            }

            return user.getId();
        }

    }

    @Override
    public UserVO userLogin(
        String account,
        String password,
        HttpServletRequest request) {
        if (StringUtils.isAnyBlank(account, password)) {
            throw new BusinessException(ResultCode.PARAMS_ERR, "用户名密码不能为空");
        }
        if (account.length() < 5
            || account.length() > 18
            || password.length() < 5
            || password.length() > 18) {
            throw new BusinessException(ResultCode.PARAMS_ERR, "用户名密码错误");
        }

        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes(StandardCharsets.UTF_8));
        User user = lambdaQuery().eq(User::getAccount, account)
            .eq(User::getPassword, encryptPassword)
            .one();
        if (user == null) {
            throw new BusinessException(ResultCode.NULL_ERR, "用户名密码错误");
        }

        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        request.getSession().setAttribute(RedisConstant.USER_LOGIN_STATE, userDTO);

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public boolean userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }
        if (UserHolder.getUser() == null) {
            throw new BusinessException(ResultCode.NULL_ERR, "未登录");
        }

        request.getSession().removeAttribute(RedisConstant.USER_LOGIN_STATE);
        return true;
    }

    @Override
    public boolean isAdmin() {
        UserDTO userDTO = UserHolder.getUser();

        return userDTO != null && RoleEnum.isAdmin(userDTO.getRole());
    }
}




