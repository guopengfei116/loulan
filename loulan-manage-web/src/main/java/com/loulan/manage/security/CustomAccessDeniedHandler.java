package com.loulan.manage.security;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 自定义授权失败处理器，当用户已经登陆，但是没有权限访问某资源时在这里进行处理
 * */
public class CustomAccessDeniedHandler extends AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {

        // 响应数据编码与格式
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        // 错误提示
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", false);
        jsonObject.put("message", "您没有权限访问该资源");

        // 返回403
        response.sendError(403, jsonObject.toJSONString());
        response.getWriter().flush();
    }
}
