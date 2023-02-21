package com.pan.app.service;

import com.pan.app.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pan.app.model.vo.UserVo;

import javax.servlet.http.HttpServletRequest;

/**
* @author Mr.Pan
* @description 针对表【mp_user】的数据库操作Service
* @createDate 2023-02-21 16:25:21
*/
public interface UserService extends IService<User> {
    void validUser(User user, boolean add);

    UserVo userLogin(String userAccount, String userPassword, HttpServletRequest request);

    boolean userLogout(HttpServletRequest request);

    long userRegist(String userAccount, String userPassword, String checkPassword);

    boolean isAdmin();
}
