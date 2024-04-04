package com.pan.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pan.app.mapper.SysLoginLogMapper;
import com.pan.app.model.entity.SysLoginLog;
import com.pan.app.service.SysLoginLogService;
import org.springframework.stereotype.Service;

/**
 * @author Mr.Pan
 * @description 针对表【sys_login_info(系统访问记录)】的数据库操作Service实现
 * @createDate 2024-03-27 20:53:01
 */
@Service
public class SysLoginLogServiceImpl
    extends ServiceImpl<SysLoginLogMapper, SysLoginLog>
    implements SysLoginLogService {

}




