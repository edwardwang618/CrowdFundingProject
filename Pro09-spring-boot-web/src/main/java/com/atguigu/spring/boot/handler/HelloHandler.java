package com.atguigu.spring.boot.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloHandler {
    
    @ResponseBody
    @RequestMapping("/hello/spring/boot")
    public String helloSpringBoot() {
        return "Hello Spring Boot!";
    }
    
}
