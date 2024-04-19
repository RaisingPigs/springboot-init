package com.pan.app.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pan.app.model.converter.login.SysLoginLogConverter;
import com.pan.app.model.converter.login.SysLoginLogVOConverter;
import com.pan.app.model.entity.SysLoginLog;
import com.pan.app.model.req.login.SysLoginLogQueryReq;
import com.pan.app.model.vo.login.SysLoginLogVO;
import com.pan.app.service.SysLoginLogService;
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
@RequestMapping("/login-log")
@RequiredArgsConstructor
@SaCheckRole("admin")
public class SysLoginLogController {
    private final SysLoginLogService sysLoginLogService;

    @PostMapping("/list/page")
    public IPage<SysLoginLogVO> list(
        @RequestBody SysLoginLogQueryReq sysLoginLogQueryReq) {
        SysLoginLog sysLoginLog = SysLoginLogConverter.INSTANCE.toSysLoginLog(sysLoginLogQueryReq);

        long pageNum = sysLoginLogQueryReq.getPageNum();
        long pageSize = sysLoginLogQueryReq.getPageSize();
        String sortField = sysLoginLogQueryReq.getSortField();
        boolean sortOrder = sysLoginLogQueryReq.isSortOrder();
        String username = sysLoginLogQueryReq.getUsername();
        sysLoginLog.setUsername(null);

        QueryWrapper<SysLoginLog> wrapper = new QueryWrapper<>(sysLoginLog);
        /*使用userName字段做模糊查询*/
        wrapper.orderBy(StringUtils.isNotBlank(sortField), sortOrder, sortField);

        LambdaQueryWrapper<SysLoginLog> lambdaQueryWrapper = wrapper.lambda()
            .like(StringUtils.isNotBlank(username), SysLoginLog::getUsername, username)
            .orderBy(true, false, SysLoginLog::getCreateTime);

        IPage<SysLoginLog> loginLogPage = sysLoginLogService.page(new Page<>(pageNum, pageSize), lambdaQueryWrapper);

        return loginLogPage.convert(SysLoginLogVOConverter.INSTANCE::toSysLoginLogVO);
    }
}
