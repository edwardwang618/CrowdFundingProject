package com.atguigu.crowd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@MapperScan("com.atguigu.crowd.mapper")
@EnableDiscoveryClient
@SpringBootApplication
public class CrowdMainType {
	
	public static void main(String[] args) {
		SpringApplication.run(CrowdMainType.class, args);
	}

}
