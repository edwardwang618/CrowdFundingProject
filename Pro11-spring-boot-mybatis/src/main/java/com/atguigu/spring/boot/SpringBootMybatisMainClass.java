package com.atguigu.spring.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.atguigu.spring.boot.mapper")
@SpringBootApplication
public class SpringBootMybatisMainClass {
    
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMybatisMainClass.class, args);
    }
    
}
