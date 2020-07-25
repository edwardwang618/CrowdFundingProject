package com.atguigu.spring.handler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewHandler {
	
	@RequestMapping("/test/thymleaf")
	public String testThymeleaf(Model model) {
		
		model.addAttribute("attrName", "<font color='red'>attrValue</font>");
		
		// handler方法返回视图名称，拼接前后缀
		return "hello";
	}

}
