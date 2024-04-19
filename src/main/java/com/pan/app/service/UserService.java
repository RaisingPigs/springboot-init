package com.pan.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pan.app.model.entity.User;

/**
 * @author Mr.Pan
 * @description 针对表【mp_user】的数据库操作Service
 * @createDate 2023-02-21 16:25:21
 */
public interface UserService extends IService<User> {
    boolean isAdmin();

    boolean saveUser(User user);
}
