package com.pan.app.event;


import com.pan.app.model.entity.SysLoginLog;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-03-28 15:37
 **/
public class LoginLogEvent<S> extends BaseEvent<S, SysLoginLog> {
    public LoginLogEvent(S source, SysLoginLog data) {
        super(source, data);
    }
}
