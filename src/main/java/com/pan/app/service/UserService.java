package com.pan.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pan.app.model.entity.User;
import com.pan.app.model.vo.user.UserVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author Mr.Pan
* @description 针对表【mp_user】的数据库操作Service
* @createDate 2023-02-21 16:25:21
*/
public interface UserService extends IService<User> {
    void validUser(User user, boolean add);

    UserVO userLogin(String username, String password, HttpServletRequest request);

    boolean userLogout(HttpServletRequest request);

    long userRegist(String username, String password, String checkPassword);

    boolean isAdmin();

    boolean saveUser(User user);
}
