package com.pan.app.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2023-02-21 15:50
 **/
@Data
@ApiModel("分页请求")
public class PageRequest {
    @ApiModelProperty(value = "当前页", example = "1")
    private long pagenum = 1;

    @ApiModelProperty(value = "页大小", example = "5")
    private long pagesize = 5;

    @ApiModelProperty(value = "排序字段", example = "age")
    private String sortField;

    @ApiModelProperty(value = "true-升序 false-降序", example = "true")
    private boolean sortOrder = true;
}
