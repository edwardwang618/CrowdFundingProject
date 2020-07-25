package com.atguigu.crowd.funding.ajax.entity;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeParam {
	
	private List<Employee> empList;
	
	public EmployeeParam() {
		// TODO Auto-generated constructor stub
	}

	public EmployeeParam(List<Employee> empList) {
		super();
		this.empList = empList;
	}
	
}
