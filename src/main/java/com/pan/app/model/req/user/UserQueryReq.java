package com.pan.app.model.req.user;

import com.pan.app.common.req.PageReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2023-02-21 16:22
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryReq extends PageReq implements Serializable {
    private static final long serialVersionUID = 1L;
  
    /**
     * 用户昵称
     */
    private String name;
    /**
     * 账号
     */
    private String username;
}
