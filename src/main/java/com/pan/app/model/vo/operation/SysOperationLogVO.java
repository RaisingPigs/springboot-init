package com.pan.app.model.vo.operation;

import com.pan.app.model.enums.operation.BusinessType;
import com.pan.app.model.enums.operation.Method;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-04-04 19:32
 **/
@Data
public class SysOperationLogVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 日志主键
     */
    private Long id;
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
     * 操作人ID
     */
    private Long operatorId;
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

    private LocalDateTime operationTime;
}
