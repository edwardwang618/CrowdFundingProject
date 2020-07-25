package com.atguigu.crowd.funding.test;

import com.atguigu.crowd.funding.entity.Admin;
import com.atguigu.crowd.funding.entity.Role;
import com.atguigu.crowd.funding.mapper.AdminMapper;
import com.atguigu.crowd.funding.mapper.RoleMapper;
import com.atguigu.crowd.funding.service.api.AdminService;
import com.atguigu.crowd.funding.util.CrowdFundingUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class CrowdFundingTest {
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private AdminMapper adminMapper;
    
    @Autowired
    private RoleMapper roleMapper;
    
    @Test
    public void testSaveAdmin() {
        // for (int i = 0; i < 50; i++) {
            // roleMapper.insert(new Role(null, "role" + i));
            // roleMapper.insert(new Role(null, "AAA" + i));
        // }
        
    }
    
    @Test
    public void batchSaveAdmin() {
        // for (int i = 0; i < 200; i++) {
        //     adminMapper.insert(new Admin(null, "loginAcct" + i, "1111", "userName" + i, "email" + i + "qq.com", null));
        // }
        String psd = CrowdFundingUtils.md5("123123");
        adminMapper.insert(new Admin(1, "harry", psd, "哈利222", "harry@qq.com", null));
    }
    
    @Test
    public void testAdminMapperSearch() {
        String keyword = "";
        List<Admin> list = adminMapper.selectAdminListByKeyword(keyword);
        
        for (Admin admin : list) {
            System.out.println(admin);
        }
    }
    
    @Test
    public void testTx() {
        // adminService.updateAdmin();
    }
    
    @Test
    public void testMybatis() {
        List<Admin> adminList = adminService.getAll();
        for (Admin admin : adminList) {
            System.out.println(admin);
        }
    }
    
    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }
}
