package com.pan.app.model.req.operation;

import com.pan.app.common.req.PageReq;
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
     * 操作人员
     */
    private String operatorName;
}
