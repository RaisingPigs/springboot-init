package com.pan.app.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pan.app.model.converter.operation.SysOperationLogConverter;
import com.pan.app.model.converter.operation.SysOperationLogVOConverter;
import com.pan.app.model.entity.SysOperationLog;
import com.pan.app.model.req.operation.SysOperationLogQueryReq;
import com.pan.app.model.vo.operation.SysOperationLogVO;
import com.pan.app.service.SysOperationLogService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-04-04 19:29
 **/
@RestController
@RequestMapping("/operation-log")
@SaCheckRole("admin")
@RequiredArgsConstructor
public class SysOperationLogController {
    private final SysOperationLogService sysOperationLogService;

    @PostMapping("/list/page")
    public IPage<SysOperationLogVO> list(
        @RequestBody SysOperationLogQueryReq sysOperationLogQueryReq) {
        SysOperationLog sysOperationLog = SysOperationLogConverter.INSTANCE.toSysOperationLog(sysOperationLogQueryReq);

        long pageNum = sysOperationLogQueryReq.getPageNum();
        long pageSize = sysOperationLogQueryReq.getPageSize();
        String sortField = sysOperationLogQueryReq.getSortField();
        boolean sortOrder = sysOperationLogQueryReq.isSortOrder();
        String operatorName = sysOperationLogQueryReq.getOperatorName();
        sysOperationLog.setOperatorName(null);

        QueryWrapper<SysOperationLog> wrapper = new QueryWrapper<>(sysOperationLog);
        wrapper.orderBy(StringUtils.isNotBlank(sortField), sortOrder, sortField);

        wrapper.lambda()
            .like(StringUtils.isNotBlank(operatorName), SysOperationLog::getOperatorName, operatorName)
            .orderBy(true, false, SysOperationLog::getCreateTime);

        IPage<SysOperationLog> sysOperationLogPage = sysOperationLogService.page(new Page<>(pageNum, pageSize), wrapper);

        return sysOperationLogPage.convert(SysOperationLogVOConverter.INSTANCE::toSysOperationLogVO);
    }
}
