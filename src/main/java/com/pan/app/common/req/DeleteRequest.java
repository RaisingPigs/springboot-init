package com.pan.app.common.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2023-02-21 15:48
 **/
@Data
public class DeleteRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
}
