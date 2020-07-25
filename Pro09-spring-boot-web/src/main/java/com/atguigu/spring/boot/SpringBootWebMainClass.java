package com.atguigu.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "com.atguigu.spring.apple.handler")
public class SpringBootWebMainClass {
    
    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebMainClass.class, args);
    }
}
