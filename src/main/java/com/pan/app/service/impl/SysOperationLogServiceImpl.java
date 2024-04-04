package com.pan.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pan.app.mapper.SysOperationLogMapper;
import com.pan.app.model.entity.SysOperationLog;
import com.pan.app.service.SysOperationLogService;
import org.springframework.stereotype.Service;

/**
 * @author Mr.Pan
 * @description 针对表【sys_operation_log(操作日志记录)】的数据库操作Service实现
 * @createDate 2024-03-31 16:40:38
 */
@Service
public class SysOperationLogServiceImpl
    extends ServiceImpl<SysOperationLogMapper, SysOperationLog>
    implements SysOperationLogService {

}




