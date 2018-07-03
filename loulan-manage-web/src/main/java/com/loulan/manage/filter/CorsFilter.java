package com.loulan.manage.filter;

import com.loulan.manage.session.AuthenticationUserSessionContext;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Arrays;

public class CorsFilter implements Filter {
    // 允许跨域访问的头信息
    private static String[] allowHeaders = {
            "WithCredentials",
            "Cookie",
            "Host",
            "Accept-Language",
            "Accept",
            "Accept-Encoding",
            "CrossDomain",
            "Content-Type",
            "Authorization",
            "X-Requested-With",
            "X-File-Name",
            "Cache-Control",
            "Access-Control-Allow-Origin",
            "Access-Control-Allow-Headers",
            "Access-Control-Allow-Methods",
            "*"
    };
    // 允许跨域访问的请求类型
    private static String allowMethods = "POST, GET, OPTIONS, DELETE ,PUT";
    // 允许跨域访问的域名
    private static String allowOrigin = "http://localhost:9527";

    /**
     * 添加跨域响应头
     * */
    private void addCorsHeader(HttpServletResponse resp) {
        // 允许跨域资源访问
        resp.setHeader("Access-Control-Allow-Origin", allowOrigin);
        resp.setHeader("Access-Control-Allow-Methods", allowMethods);
        String headers = Arrays.toString(allowHeaders);
        resp.setHeader("Access-Control-Allow-Headers", headers.substring(0, headers.length() - 1).substring(1));

        // 允许跨域携带认证信息
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("XDomainRequestAllowed", "1");

        resp.addCookie(new Cookie("cookie-test", "ZiDingYiCookie"));
        resp.addHeader("session-test", "ZiDingYiCookieSession");
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        addCorsHeader(resp);

        // options请求,添加跨域响应头后直接返回200
        if("OPTIONS".equals(req.getMethod())) {
            resp.getWriter().write("success!");
            return;
        }

        // 查看获取的到cookie
        Cookie[] cookies = req.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName() + ":" + cookie.getValue());
            }
        }else {
            System.out.println("没有获取到任何cookie");
        }

        // 通过请求头拿到sessionID，然后从容器中获取session对象，然后交给spring-security进行试验
        String sessionId = req.getHeader("JSESSIONID");
        HttpSession session = AuthenticationUserSessionContext.getSession(sessionId);

        System.out.println("-----------------------------------自定义过滤器放行前logger-----------------------------------");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("-----------------------------------自定义过滤器结束-----------------------------------");
    }

    @Override
    public void destroy() {

    }
}
