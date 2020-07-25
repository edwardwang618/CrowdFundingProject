package com.atguigu.crowd.funding.exception;

import com.atguigu.crowd.funding.entity.ResultEntity;
import com.atguigu.crowd.funding.util.CrowdFundingConstant;
import com.atguigu.crowd.funding.util.CrowdFundingUtils;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class CrowdFundingExceptionResolver {

    @ExceptionHandler(value = Exception.class)
    public ModelAndView catchException(Exception exception,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws IOException {
        // 对当前请求进行检查
        boolean checkAsyncRequestResult = CrowdFundingUtils.checkAsyncRequest(request);
        
        if (checkAsyncRequestResult) {
    
            String exceptionClassName = exception.getClass().getName();
            String message = CrowdFundingConstant.EXCEPTION_MESSAGE_MAP.get(exceptionClassName);
            
            if (message == null) {
                message = "系统未知错误";
                
            }
    
            ResultEntity<Object> resultEntity = ResultEntity.failed(ResultEntity.NO_DATA, message);
            Gson gson = new Gson();
            String json = gson.toJson(resultEntity);
            
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(json);
            
            return null;
        }
    
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        
        mav.setViewName("system-error");
        
        return mav;
    }
}
