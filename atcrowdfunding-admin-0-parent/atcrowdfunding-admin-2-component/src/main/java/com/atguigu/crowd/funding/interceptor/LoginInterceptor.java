package com.atguigu.crowd.funding.interceptor;

import com.atguigu.crowd.funding.entity.Admin;
import com.atguigu.crowd.funding.entity.ResultEntity;
import com.atguigu.crowd.funding.util.CrowdFundingConstant;
import com.atguigu.crowd.funding.util.CrowdFundingUtils;
import com.google.gson.Gson;
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
    
            boolean checkAsyncRequestResult = CrowdFundingUtils.checkAsyncRequest(request);
            if (checkAsyncRequestResult) {
                ResultEntity<String> resultEntity = ResultEntity.failed(ResultEntity.NO_DATA, CrowdFundingConstant.MESSAGE_ACCESS_DENIED);
                Gson gson = new Gson();
                String json = gson.toJson(resultEntity);
                
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(json);
                
                return false;
            }
    
            request.setAttribute(CrowdFundingConstant.ATTR_NAME_MESSAGE, CrowdFundingConstant.MESSAGE_ACCESS_DENIED);
            request.getRequestDispatcher("/WEB-INF/admin-login.jsp").forward(request, response);
            return false;
        }
        
        return true;
    }
}
