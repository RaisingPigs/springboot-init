package com.pan.app.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2022-10-07 14:49
 **/
@Data
@ApiModel("响应实体类")
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = 293244422036841199L;

    @ApiModelProperty("响应状态码")
    private int code;
    @ApiModelProperty("响应数据")
    private T data;
    @ApiModelProperty("提示信息")
    private String msg;
    @ApiModelProperty("描述信息")
    private String desc;

    /*无参构造是避免jackson转换时, 无法创建创建对象而报错*/
    public BaseResponse() {
    }
    
    public BaseResponse(int code, T data, String msg, String desc) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.desc = desc;
    }

    public BaseResponse(ResultCode resultCode, T data) {
        this(resultCode.getCode(), data, resultCode.getMsg(), resultCode.getDesc());
    }

    public BaseResponse(ResultCode resultCode, String msg, String desc) {
        this(resultCode.getCode(), null, msg, desc);
    }

    public BaseResponse(ResultCode resultCode, String desc) {
        this(resultCode, resultCode.getMsg(), desc);
    }

    public BaseResponse(ResultCode resultCode) {
        this(resultCode, resultCode.getDesc());
    }
}
