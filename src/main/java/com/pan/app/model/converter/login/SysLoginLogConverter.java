package com.pan.app.model.converter.login;

import com.pan.app.model.entity.SysLoginLog;
import com.pan.app.model.req.login.SysLoginLogQueryReq;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-04-04 19:47
 **/
@Mapper
public interface SysLoginLogConverter {
    SysLoginLogConverter INSTANCE = Mappers.getMapper(SysLoginLogConverter.class);

    SysLoginLog toSysLoginLog(SysLoginLogQueryReq sysLoginLogQueryReq);
}
