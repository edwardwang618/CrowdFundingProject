package com.atguigu.crowd.funding.interceptor;

import com.atguigu.crowd.funding.entity.Admin;
import com.atguigu.crowd.funding.util.CrowdFundingConstant;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute(CrowdFundingConstant.ATTR_NAME_LOGIN_ADMIN);
        
        if (admin == null) {
            request.setAttribute(CrowdFundingConstant.ATTR_NAME_MESSAGE, CrowdFundingConstant.MESSAGE_ACCESS_DENIED);
            request.getRequestDispatcher("/WEB-INF/admin-login.jsp").forward(request, response);
            return false;
        }
        
        return true;
    }
}
