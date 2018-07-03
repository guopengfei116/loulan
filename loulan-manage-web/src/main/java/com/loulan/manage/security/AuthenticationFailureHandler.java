package com.loulan.manage.security;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Description:    自定义spring-security登陆失败后的处理器
 * Author:         coffei
 * CreateDate:     2018/7/2 15:53
 * UpdateUser:     coffei
 */
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    /**
     * Description: 如果是ajax请求则返回JSON，否则走默认的302重定向错误页面的处理方式
     * 返回的json数据结构：{"authorities":[],"username":"", "success": false, "msg": "账户或密码错误"}
     * */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        // 响应数据编码与格式
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        // 目前全部返回JSON
        Boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
        if (true) {
            // 返回数据
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("authorities", new ArrayList<>());
            jsonObject.put("username", "");
            jsonObject.put("success", false);
            jsonObject.put("message", exception.getCause() != null? exception.getCause(): "账户或密码错误");
            
            response.getWriter().print(jsonObject.toJSONString());
            response.getWriter().flush();
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
