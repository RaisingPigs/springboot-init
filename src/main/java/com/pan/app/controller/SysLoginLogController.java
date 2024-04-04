package com.pan.app.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pan.app.common.resp.BaseResponse;
import com.pan.app.common.resp.ResultCode;
import com.pan.app.common.resp.ResultUtils;
import com.pan.app.constant.PageConstant;
import com.pan.app.exception.BusinessException;
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
    public BaseResponse<IPage<SysLoginLogVO>> list(
        @RequestBody SysLoginLogQueryReq sysLoginLogQueryReq) {
        if (sysLoginLogQueryReq == null) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        /*限制爬虫*/
        if (sysLoginLogQueryReq.getPageSize() > PageConstant.MAX_PAGE_SIZE) {
            throw new BusinessException(ResultCode.PARAMS_ERR);
        }

        SysLoginLog sysLoginLog = SysLoginLogConverter.INSTANCE.toSysLoginLog(sysLoginLogQueryReq);

        long pageNum = sysLoginLogQueryReq.getPageNum();
        long pageSize = sysLoginLogQueryReq.getPageSize();
        String sortField = sysLoginLogQueryReq.getSortField();
        boolean sortOrder = sysLoginLogQueryReq.isSortOrder();
        String username = sysLoginLogQueryReq.getUsername();
        sysLoginLog.setUsername(null);

        QueryWrapper<SysLoginLog> wrapper = new QueryWrapper<>(sysLoginLog);
        /*使用userName字段做模糊查询*/
        wrapper.like(StringUtils.isNotBlank(username), "username", username);
        wrapper.orderBy(StringUtils.isNotBlank(sortField), sortOrder, sortField);

        IPage<SysLoginLog> loginLogPage = sysLoginLogService.page(new Page<>(pageNum, pageSize), wrapper);
        IPage<SysLoginLogVO> loginLogVOPage = loginLogPage.convert(SysLoginLogVOConverter.INSTANCE::toSysLoginLogVO);

        return ResultUtils.success(loginLogVOPage);
    }
}
