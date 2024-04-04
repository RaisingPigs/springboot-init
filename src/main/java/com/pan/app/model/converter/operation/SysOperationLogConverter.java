package com.pan.app.model.converter.operation;

import com.pan.app.model.entity.SysOperationLog;
import com.pan.app.model.req.operation.SysOperationLogQueryReq;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-04-04 19:48
 **/
@Mapper
public interface SysOperationLogConverter {
    SysOperationLogConverter INSTANCE = Mappers.getMapper(SysOperationLogConverter.class);

    SysOperationLog toSysOperationLog(SysOperationLogQueryReq sysOperationLogQueryReq);
}
