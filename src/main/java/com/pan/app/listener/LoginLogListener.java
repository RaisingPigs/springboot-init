package com.pan.app.listener;

import com.pan.app.config.ThreadPoolConfig;
import com.pan.app.event.LoginLogEvent;
import com.pan.app.model.entity.SysLoginLog;
import com.pan.app.service.SysLoginLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-03-28 15:31
 **/
@Component
@RequiredArgsConstructor
public class LoginLogListener {
    private final SysLoginLogService sysLoginLogService;

    @Async(ThreadPoolConfig.EVENT_THREAD_POOL)
    @EventListener
    public void listenLoginLog(LoginLogEvent<?> event) {
        SysLoginLog loginLog = event.getData();
        sysLoginLogService.save(loginLog);
    }
}
