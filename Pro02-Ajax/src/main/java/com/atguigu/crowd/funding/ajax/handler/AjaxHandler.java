package com.atguigu.crowd.funding.ajax.handler;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.crowd.funding.ajax.entity.Employee;
import com.atguigu.crowd.funding.ajax.entity.EmployeeParam;
import com.atguigu.crowd.funding.ajax.entity.ResultEntity;

@Controller
public class AjaxHandler {
	
	@ResponseBody
	@RequestMapping("/ajax/send/form/list/data")
	public ResultEntity<String> receiveFormListData(EmployeeParam employeeParam) {
		
		List<Employee> empList = employeeParam.getEmpList();
		
		for (Employee employee : empList) {
			System.out.println(employee);
		}
		
		return ResultEntity.successWithoutData();
	}
	
	@ResponseBody
	@RequestMapping("/ajax/send/json/data")
	public ResultEntity<String> receiveJsonData(@RequestBody List<Employee> empList) {
		
		for (Employee employee : empList) {
			System.out.println(employee);
		}
		
		return ResultEntity.successWithoutData();
	}
	
	@ResponseBody
	@RequestMapping("/ajax/send/form/data")
	public ResultEntity<String> receiveFormData(Employee employee) {
		
		System.out.println(employee);
		
		return ResultEntity.successWithoutData();
	}

}
