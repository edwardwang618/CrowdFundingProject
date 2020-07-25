package com.atguigu.dubbo.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.dubbo.api.EmployeeRemoteService;
import com.atguigu.dubbo.entity.Employee;

@Controller
public class EmployeeHandler {
	
	@Autowired
	private EmployeeRemoteService employeeRemoteService;
	
	@RequestMapping("/get/emp/list")
	public String getEmpList() {
		
		Employee employee = new Employee(666, "empName666", 666.66);
		
		List<Employee> list = employeeRemoteService.getEmployeeByConditionRemote(employee);
		
		for (Employee employee2 : list) {
			System.out.println(employee2);
		}
		
		return "target";
	}

}
