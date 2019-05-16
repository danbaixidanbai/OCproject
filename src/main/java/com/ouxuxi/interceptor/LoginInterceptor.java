package com.ouxuxi.interceptor;

import com.ouxuxi.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // 检查每个到来的请求对应的session域中是否有登录标识
        User user= (User) request.getSession().getAttribute("user");
        if (null == user || user.getUserLoginName().equals(" ")) {
            // 未登录，重定向到登录页
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
