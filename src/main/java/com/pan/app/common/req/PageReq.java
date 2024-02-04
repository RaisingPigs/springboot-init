package com.pan.app.common.req;

import lombok.Data;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2023-02-21 15:50
 **/
@Data
public class PageReq {
    private long pageNum = 1;

    private long pageSize = 5;

    private String sortField;

    private boolean sortOrder = true;
}
