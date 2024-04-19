package com.pan.app.model.req.login;

import com.pan.app.common.req.PageReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-04-04 19:42
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SysLoginLogQueryReq extends PageReq implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户账号
     */
    private String username;
}
