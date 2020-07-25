package com.atguigu.crowd.funding.service.impl;

import com.atguigu.crowd.funding.entity.Admin;
import com.atguigu.crowd.funding.entity.AdminExample;
import com.atguigu.crowd.funding.mapper.AdminMapper;
import com.atguigu.crowd.funding.service.api.AdminService;
import com.atguigu.crowd.funding.util.CrowdFundingUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {
    
    @Autowired
    private AdminMapper adminMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }
    
    @Override
    public void updateAdmin(Admin admin) {
        String userPswd = admin.getUserPswd();
        userPswd = CrowdFundingUtils.md5(userPswd);
        admin.setUserPswd(userPswd);
        
        adminMapper.updateByPrimaryKey(admin);
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
    
    @Override
    public void saveAdmin(Admin admin) {
        String userPswd = admin.getUserPswd();
        userPswd = passwordEncoder.encode(userPswd);
        admin.setUserPswd(userPswd);
        
        adminMapper.insert(admin);
    }
    
    @Override
    public Admin getAdminById(Integer adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }
}
