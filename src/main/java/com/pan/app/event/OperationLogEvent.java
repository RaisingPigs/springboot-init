package com.pan.app.event;

import com.pan.app.model.entity.SysOperationLog;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-03-31 17:23
 **/
public class OperationLogEvent<S> extends BaseEvent<S, SysOperationLog> {
    public OperationLogEvent(S source, SysOperationLog data) {
        super(source, data);
    }
}
