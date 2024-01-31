package com.pan.app.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-01-31 17:35
 **/
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("创建者ID")
    @TableField(value = "creator_id", fill = FieldFill.INSERT)
    private Long creatorId;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * deleted字段请在数据库中 设置为tinyInt   并且非null   默认值为0
     */
    @ApiModelProperty("删除标志（0代表存在 1代表删除）")
    @TableField("deleted")
    @TableLogic
    private Boolean deleted;
}
