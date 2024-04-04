package com.pan.app.model.converter.operation;

import com.pan.app.model.entity.SysOperationLog;
import com.pan.app.model.vo.operation.SysOperationLogVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-04-04 19:48
 **/
@Mapper
public interface SysOperationLogVOConverter {
    SysOperationLogVOConverter INSTANCE = Mappers.getMapper(SysOperationLogVOConverter.class);

    @Mapping(source = "createTime", target = "operationTime")
    SysOperationLogVO toSysOperationLogVO(SysOperationLog sysOperationLog);
}
