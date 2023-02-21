package com.pan.app.model.request.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2023-02-21 16:34
 **/
@Data
public class UserRegistRequest implements Serializable {
    private String userAccount;

    private String userPassword;
    
    private String checkPassword;

    private static final long serialVersionUID = 1L;

}
