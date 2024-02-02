package com.pan.app.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import com.pan.app.utils.AuthUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StpInterfaceImpl implements StpInterface {
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return null;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> list = new ArrayList<>();
        String role = AuthUtils.getLoginUser().getRole().getDesc();
        list.add(role);

        return list;
    }

}