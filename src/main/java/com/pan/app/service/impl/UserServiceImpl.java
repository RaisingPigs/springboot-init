package com.pan.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pan.app.common.ResultCode;
import com.pan.app.constant.UserConstant;
import com.pan.app.exception.BusinessException;
import com.pan.app.mapper.UserMapper;
import com.pan.app.model.entity.User;
import com.pan.app.model.enums.GenderEnum;
import com.pan.app.model.vo.UserVo;
import com.pan.app.service.UserService;
import com.pan.app.utils.UserHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * @author Mr.Pan
 * @description 针对表【mp_user】的数据库操作Service实现
 * @createDate 2023-02-21 16:25:21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Resource
    private UserMapper userMapper;
    /*加盐*/
    private static final String SALT = "pan";

    @Override
    public void validUser(User user, boolean add) {
        if (user == null) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        String userAccount = user.getUserAccount();
        String username = user.getUserName();
        String password = user.getUserPassword();
        String userAvatar = user.getUserAvatar();
        Integer gender = user.getGender();
        String userRole = user.getUserRole();

        /*创建时，所有参数必须非空*/
        if (add) {
            if (StringUtils.isAnyBlank(userAccount, username, password, userAvatar, userRole) || gender == null) {
                throw new BusinessException(ResultCode.PARAMS_ERR);
            }
        }

        if (StringUtils.isNotBlank(username) && username.length() > 16) {
            throw new BusinessException(ResultCode.PARAMS_ERR, "用户名过长");
        }

        if (StringUtils.isNotBlank(password) && password.length() > 16) {
            throw new BusinessException(ResultCode.PARAMS_ERR, "密码过长");
        }

        if (gender != null && GenderEnum.getValues().contains(gender)) {
            throw new BusinessException(ResultCode.PARAMS_ERR, "性别只能为1或0");
        }
    }

    @Override
    public long userRegist(String userAccount, String userPassword, String checkPassword) {
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        if (userAccount.length() < 5
                || userAccount.length() > 18
                || userPassword.length() < 5
                || userPassword.length() > 18) {
            throw new BusinessException(ResultCode.PARAMS_ERR, "用户名密码长度不符合规范");
        }

        if (!checkPassword.equals(userPassword)) {
            throw new BusinessException(ResultCode.PARAMS_ERR, "两次输入密码不一致");
        }

        synchronized (userAccount.intern()) {
            int count = lambdaQuery().eq(User::getUserAccount, userAccount).count();
            if (count > 0) {
                throw new BusinessException(ResultCode.SAVE_ERR, "该用户名已被使用");
            }

            /*加密*/
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes(StandardCharsets.UTF_8));

            User user = new User();
            user.setUserAccount(userAccount);
            user.setUserPassword(userPassword);
            boolean save = save(user);
            if (!save) {
                throw new BusinessException(ResultCode.SAVE_ERR);
            }

            return user.getId();
        }

    }

    @Override
    public UserVo userLogin(
            String userAccount,
            String userPassword,
            HttpServletRequest request) {
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ResultCode.PARAMS_ERR, "用户名密码不能为空");
        }
        if (userAccount.length() < 5
                || userAccount.length() > 18
                || userPassword.length() < 5
                || userPassword.length() > 18) {
            throw new BusinessException(ResultCode.PARAMS_ERR, "用户名密码错误");
        }

        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes(StandardCharsets.UTF_8));
        User user = lambdaQuery().eq(User::getUserAccount, userAccount)
                .eq(User::getUserPassword, userPassword)
                .one();
        if (user == null) {
            throw new BusinessException(ResultCode.NULL_ERR, "用户名密码错误");
        }

        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, userVo);
        return userVo;
    }

    @Override
    public boolean userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }
        if (UserHolder.getUser() == null) {
            throw new BusinessException(ResultCode.NULL_ERR, "未登录");
        }

        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
        return true;
    }

    @Override
    public boolean isAdmin() {
        UserVo user = UserHolder.getUser();

        return user != null && UserConstant.ADMIN_ROLE.equals(user.getUserRole());
    }
}




