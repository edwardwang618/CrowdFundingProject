package com.atguigu.dubbo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer empId;
    private String empName;
    private Double salary;
}
