package com.loulan.shop.security;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 如果用户未登陆或登陆失效，那么就会进入该入口进行处理，默认302重定向到登陆页，这里改为401
 * 这里已经销毁了上下文，无法调用SecurityContextHolder.getContext()
 * */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {

        // 响应数据编码与格式
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        // 错误提示
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", false);
        jsonObject.put("message", "用户认证失败");

        // 返回401
        response.sendError(401, jsonObject.toJSONString());
        response.getWriter().flush();
    }
}
