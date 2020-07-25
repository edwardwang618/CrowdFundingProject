package com.atguigu.dubbo.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.atguigu.dubbo.api.EmployeeRemoteService;
import com.atguigu.dubbo.entity.Employee;

public class EmployeeServiceImpl implements EmployeeRemoteService {

	@Override
	public List<Employee> getEmployeeByConditionRemote(Employee employee) {
		
		System.out.println(employee);
		
		List<Employee> empList = new ArrayList<>();
		
		empList.add(new Employee(111, "empName111", 111.11));
		empList.add(new Employee(222, "empName222", 222.22));
		empList.add(new Employee(333, "empName333", 333.33));
		
		return empList;
	}

}
