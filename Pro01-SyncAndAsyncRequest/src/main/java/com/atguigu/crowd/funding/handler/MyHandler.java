package com.atguigu.crowd.funding.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyHandler {
    
    @ResponseBody
    @RequestMapping("/test/request")
    public String testRequest() {
        return "server response";
    }
}
