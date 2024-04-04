package com.pan.app.listener;

import com.pan.app.config.ThreadPoolConfig;
import com.pan.app.event.OperationLogEvent;
import com.pan.app.model.entity.SysOperationLog;
import com.pan.app.service.SysOperationLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-03-31 17:22
 **/
@Component
@RequiredArgsConstructor
public class OperationLogListener {
    private final SysOperationLogService sysOperationLogService;

    @Async(ThreadPoolConfig.EVENT_THREAD_POOL)
    @EventListener
    public void listenOperationLog(OperationLogEvent<?> operationLogEvent) {
        SysOperationLog sysOperationLog = operationLogEvent.getData();
        sysOperationLogService.save(sysOperationLog);
    }
}
