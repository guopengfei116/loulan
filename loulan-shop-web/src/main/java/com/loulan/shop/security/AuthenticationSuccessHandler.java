package com.loulan.shop.security;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
* Description:    自定义spring-security登陆成功后的处理器
* Author:         coffei
* CreateDate:     2018/7/2 15:53
* UpdateUser:     coffei
*/
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    /**
     * Description: 如果是ajax请求则返回JSON，否则走默认的302重定向成功页面的处理方式
     * 返回的json数据结构：{"authorities":[{"authority":"ROLE_USER"}],"username":"feifei", "success": true, "msg": "登陆成功"}
     * */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException {

        // 响应数据编码与格式
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        // 返回JSON数据
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("authorities", auth.getAuthorities());
        jsonObject.put("username", auth.getName());
        jsonObject.put("success", true);
        jsonObject.put("message", "登陆成功");

        response.getWriter().print(jsonObject.toJSONString());
        response.getWriter().flush();
    }

}
