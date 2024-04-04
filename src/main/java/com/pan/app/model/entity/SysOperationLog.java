package com.pan.app.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pan.app.common.entity.BaseEntity;
import com.pan.app.model.enums.operation.BusinessType;
import com.pan.app.model.enums.operation.Method;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 操作日志记录
 * @TableName sys_operation_log
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_operation_log")
@NoArgsConstructor
public class SysOperationLog extends BaseEntity {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 日志主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 业务类型（0其它 1新增 2修改 3删除 4查询）
     */
    @TableField(value = "business_type")
    private BusinessType businessType;
    /**
     * 请求方式
     */
    @TableField(value = "req_method")
    private Method reqMethod;
    /**
     * 请求模块
     */
    @TableField(value = "req_module")
    private String reqModule;
    /**
     * 请求URL
     */
    @TableField(value = "req_url")
    private String reqUrl;
    /**
     * 调用方法
     */
    @TableField(value = "called_method")
    private String calledMethod;
    /**
     * 操作人ID
     */
    @TableField(value = "operator_id")
    private Long operatorId;
    /**
     * 操作人员
     */
    @TableField(value = "operator_name")
    private String operatorName;
    /**
     * 操作人员ip
     */
    @TableField(value = "operator_ip")
    private String operatorIp;
    /**
     * 请求参数
     */
    @TableField(value = "req_param")
    private String reqParam;
    /**
     * 返回参数
     */
    @TableField(value = "req_result")
    private String reqResult;

    public SysOperationLog(BusinessType businessType, Method reqMethod, String reqModule, String reqUrl, String calledMethod, Long operatorId, String operatorName, String operatorIp, String reqParam, String reqResult) {
        this.businessType = businessType;
        this.reqMethod = reqMethod;
        this.reqModule = reqModule;
        this.reqUrl = reqUrl;
        this.calledMethod = calledMethod;
        this.operatorId = operatorId;
        this.operatorName = operatorName;
        this.operatorIp = operatorIp;
        this.reqParam = reqParam;
        this.reqResult = reqResult;
        setCreatorId(this.operatorId);
        setUpdaterId(this.operatorId);
    }
}