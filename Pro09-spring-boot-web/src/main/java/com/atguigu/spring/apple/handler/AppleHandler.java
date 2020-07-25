package com.atguigu.spring.apple.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AppleHandler {
    
    @ResponseBody
    @RequestMapping("/apple")
    public String apple() {
        return "apple";
    }
}
