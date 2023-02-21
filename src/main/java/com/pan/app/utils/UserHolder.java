package com.pan.app.utils;

import com.pan.app.model.entity.User;
import com.pan.app.model.vo.UserVo;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2023-02-21 22:16
 **/
public class UserHolder {
    private static final ThreadLocal<UserVo> tl = new ThreadLocal<>();

    public static void saveUser(UserVo userVo) {
        tl.set(userVo);
    }
    
    public static UserVo getUser() {
        return tl.get();
    }

    public static void removeUser() {
        tl.remove();
    }
}
