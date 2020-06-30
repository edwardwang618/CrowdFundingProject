package com.atguigu.crowd.funding.service.impl;

import com.atguigu.crowd.funding.entity.Admin;
import com.atguigu.crowd.funding.entity.AdminExample;
import com.atguigu.crowd.funding.mapper.AdminMapper;
import com.atguigu.crowd.funding.service.api.AdminService;
import com.atguigu.crowd.funding.util.CrowdFundingUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {
    
    @Autowired
    private AdminMapper adminMapper;
    
    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }
    
    @Override
    public void updateAdmin() {
        adminMapper.updateByPrimaryKey(new Admin(1, "harry333", "123123", "哈利3", "harry@qq.com", null));
        // System.out.println(10 / 0);
        adminMapper.updateByPrimaryKey(new Admin(2, "potter333", "123123", "波特3", "potter@qq.com", null));
    }
    
    @Override
    public Admin login(String loginAcct, String userPswd) {
        // 根据loginAcct查数据库
        AdminExample adminExample = new AdminExample();
        adminExample.createCriteria().andLoginAcctEqualTo(loginAcct);
    
        List<Admin> list = adminMapper.selectByExample(adminExample);
        
        if (!CrowdFundingUtils.collectionEffective(list)) {
            // 如果查询结果无效，直接返回null
            return null;
        }
    
        Admin admin = list.get(0);
        if (admin == null) {
            return null;
        }
    
        String userPswdDataBase = admin.getUserPswd();
        String userPswdBrowser = CrowdFundingUtils.md5(userPswd);
        
        // 两个密码相等，返回admin
        if (Objects.equals(userPswdDataBase, userPswdBrowser)) {
            return admin;
        }
        
        return null;
    }
    
    @Override
    public PageInfo<Admin> queryForKeywordSearch(Integer pageNum, Integer pageSize, String keyword) {
        PageHelper.startPage(pageNum, pageSize);
    
        List<Admin> list = adminMapper.selectAdminListByKeyword(keyword);
        
        return new PageInfo<>(list);
    }
    
    @Override
    public void batchRemove(List<Integer> adminIdList) {
        AdminExample adminExample = new AdminExample();
        // 将java转换为sql语句中where
        adminExample.createCriteria().andIdIn(adminIdList);
        
        adminMapper.deleteByExample(adminExample);
    }
}
