package com.pan.app.common.req;

import lombok.Data;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2023-02-21 15:50
 **/
@Data
public class PageRequest {
    private long pagenum = 1;

    private long pagesize = 5;

    private String sortField;

    private boolean sortOrder = true;
}
