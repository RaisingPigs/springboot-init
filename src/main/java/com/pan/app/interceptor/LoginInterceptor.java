package com.pan.app.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pan.app.common.resp.BaseResponse;
import com.pan.app.common.resp.ResultCode;
import com.pan.app.common.resp.ResultUtils;
import com.pan.app.constant.RedisConstant;
import com.pan.app.model.dto.user.UserDTO;
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
        UserDTO userDTO = (UserDTO) request.getSession().getAttribute(RedisConstant.USER_LOGIN_STATE);
        if (userDTO == null) {
            respMsg(response, ResultUtils.failed(ResultCode.NO_LOGIN));
            return false;
        }
        UserHolder.saveUser(userDTO);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserHolder.removeUser();
    }

    private void respMsg(
        HttpServletResponse response,
        BaseResponse<?> baseResponse) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String json = objectMapper.writeValueAsString(baseResponse);
        response.getWriter().append(json);
    }
}
