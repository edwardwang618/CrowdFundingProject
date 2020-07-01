package com.atguigu.crowd.funding.handler;

import com.atguigu.crowd.funding.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RoleHandler {
    
    @Autowired
    private RoleService roleService;
    
}
