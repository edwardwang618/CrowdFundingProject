package com.atguigu.spring.boot.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewHandler {
	
	@RequestMapping("/to/jsp/page")
	public String toJspPage() {
		return "target";
	}

}
