package com.pan.app.common.req;

import com.pan.app.common.param.ParamChecker;
import com.pan.app.common.resp.BizCode;
import com.pan.app.constant.PageConstant;
import com.pan.app.exception.BizException;
import lombok.Data;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2023-02-21 15:50
 **/
@Data
public class PageReq implements ParamChecker {
    private long pageNum = 1;

    private long pageSize = 5;

    private String sortField;

    private boolean sortOrder = true;

    @Override
    public void checkParam() {
        /*限制爬虫*/
        if (getPageSize() > PageConstant.MAX_PAGE_SIZE) {
            throw new BizException(BizCode.PARAMS_ERR);
        }
    }
}
