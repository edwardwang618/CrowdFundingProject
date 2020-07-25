package com.atguigu.dubbo.api;

import java.util.List;

import com.atguigu.dubbo.entity.Employee;

public interface EmployeeRemoteService {
	
	List<Employee> getEmployeeByConditionRemote(Employee employee);

}
