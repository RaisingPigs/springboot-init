package com.pan.app.utils;


import com.pan.app.model.dto.user.UserDTO;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2023-02-21 22:16
 **/
public class UserHolder {
    private static final ThreadLocal<UserDTO> THREAD_LOCAL = new ThreadLocal<>();

    public static void saveUser(UserDTO userDTO) {
        THREAD_LOCAL.set(userDTO);
    }

    public static UserDTO getUser() {
        return THREAD_LOCAL.get();
    }

    public static void removeUser() {
        THREAD_LOCAL.remove();
    }
}
