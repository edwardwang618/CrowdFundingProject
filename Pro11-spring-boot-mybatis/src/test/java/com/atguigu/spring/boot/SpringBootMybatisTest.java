package com.atguigu.spring.boot;

import com.atguigu.spring.boot.entity.Emp;
import com.atguigu.spring.boot.service.api.EmpService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMybatisTest {
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private EmpService empService;
    
    @Test
    public void testGetEmpList() {
        List<Emp> list = empService.getAll();
        list.forEach(System.out::println);
    }
    
    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }
}
