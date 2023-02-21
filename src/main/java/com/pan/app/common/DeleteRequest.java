package com.pan.app.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2023-02-21 15:48
 **/
@Data
@ApiModel("删除请求类")
public class DeleteRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "待删除主键id", required = true, example = "111111")
    private Long id;
}
