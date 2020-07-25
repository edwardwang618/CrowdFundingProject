package com.atguigu.spring.session.handler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class SessionTestHandler {
    
    @RequestMapping("/test/session/query")
    public String testSessionQuery(HttpSession session) {
        Object attrValue = session.getAttribute("attrNameGoodMorning");
        return "value getted: " + attrValue;
    }
}
