package com.pan.app.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pan.app.common.BaseResponse;
import com.pan.app.common.ResultCode;
import com.pan.app.common.ResultUtils;
import com.pan.app.constant.UserConstant;
import com.pan.app.model.vo.UserVo;
import com.pan.app.utils.UserHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2023-02-21 22:15
 **/
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserVo userVo = (UserVo) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if (userVo == null) {
            respMsg(response, ResultUtils.failed(ResultCode.NO_LOGIN));
            return false;
        }
        UserHolder.saveUser(userVo);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }

    private void respMsg(
            HttpServletResponse response,
            BaseResponse baseResponse) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String json = objectMapper.writeValueAsString(baseResponse);
        response.getWriter().append(json);
    }
}
