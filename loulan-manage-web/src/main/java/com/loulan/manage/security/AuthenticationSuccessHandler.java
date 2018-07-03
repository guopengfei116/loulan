package com.loulan.manage.security;

import com.alibaba.fastjson.JSONObject;
import com.loulan.manage.session.AuthenticationUserSessionContext;
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
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {

        // 响应数据编码与格式
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        // 目前全部返回JSON
        Boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
        if (true) {
            /*
            * 获取登陆用户对象：
            * class: org.springframework.security.core.userdetails.User
            * properties: Username，Password， Enabled，AccountNonExpired，credentialsNonExpired，AccountNonLocked，
            * properties: Granted Authorities: ROLE_USER, status = 1
            * 方法示例：Object principal = auth.getPrincipal();
            * */

            /**
             * 获取认证信息：
             * 访问地址，sessionID，所有权限
             * */
            WebAuthenticationDetails details = (WebAuthenticationDetails) auth.getDetails();
            String RemoteAddress = details.getRemoteAddress();
            String SessionId = details.getSessionId();
            System.out.println("----RemoteAddress----" + RemoteAddress);
            System.out.println("----SessionId-----" + SessionId);
            List<GrantedAuthority> authorities = (List<GrantedAuthority>) auth.getAuthorities();
            for (GrantedAuthority grantedAuthority : authorities) {
                System.out.println("----Authority----" + grantedAuthority.getAuthority());
            }

            // 因为跨越无法set-cookie，所以通过响应头把sessionID传给客户端，并把session对象保存到自己的容器中
            String sessionId = request.getSession().getId();
            response.setHeader("JSESSIONID", sessionId);
            AuthenticationUserSessionContext.addSession(request.getSession());

            // 返回数据
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("authorities", auth.getAuthorities());
            jsonObject.put("username", auth.getName());
            jsonObject.put("success", true);
            jsonObject.put("message", "登陆成功");

            response.getWriter().print(jsonObject.toJSONString());
            response.getWriter().flush();
        } else {
            super.onAuthenticationSuccess(request, response, auth);
        }
    }

}
