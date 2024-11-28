package com.pan.app.util;

import cn.hutool.http.ContentType;
import com.pan.app.common.resp.BizResp;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-02-02 08:16
 **/
public class WebUtils {
    public static void respMsg(
            HttpServletResponse response,
            BizResp<?> bizResp) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(ContentType.JSON.getValue());

        String json = JSONUtils.toJsonStr(bizResp);
        response.getWriter().append(json);
    }
}
