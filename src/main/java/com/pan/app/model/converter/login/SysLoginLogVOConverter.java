package com.pan.app.model.converter.login;

import com.pan.app.model.entity.SysLoginLog;
import com.pan.app.model.vo.login.SysLoginLogVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-04-04 19:47
 **/
@Mapper
public interface SysLoginLogVOConverter {
    SysLoginLogVOConverter INSTANCE = Mappers.getMapper(SysLoginLogVOConverter.class);

    @Mapping(source = "createTime", target = "loginTime")
    SysLoginLogVO toSysLoginLogVO(SysLoginLog sysLoginLog);
}
