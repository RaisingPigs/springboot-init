package com.pan.app.model.req.operation;

import com.pan.app.common.req.PageReq;
import com.pan.app.model.enums.operation.BusinessType;
import com.pan.app.model.enums.operation.Method;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-04-04 19:45
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SysOperationLogQueryReq extends PageReq implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 业务类型（0其它 1新增 2修改 3删除 4查询）
     */
    private BusinessType businessType;
    /**
     * 请求方式
     */
    private Method reqMethod;
    /**
     * 请求模块
     */
    private String reqModule;
    /**
     * 请求URL
     */
    private String reqUrl;
    /**
     * 调用方法
     */
    private String calledMethod;
    /**
     * 操作人员
     */
    private String operatorName;
    /**
     * 操作人员ip
     */
    private String operatorIp;
    /**
     * 请求参数
     */
    private String reqParam;
    /**
     * 返回参数
     */
    private String reqResult;
}
