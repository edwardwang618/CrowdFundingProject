package com.atguigu.crowd.funding.service.impl;

import com.atguigu.crowd.funding.config.SecurityAdmin;
import com.atguigu.crowd.funding.entity.Admin;
import com.atguigu.crowd.funding.entity.AdminExample;
import com.atguigu.crowd.funding.entity.Auth;
import com.atguigu.crowd.funding.entity.Role;
import com.atguigu.crowd.funding.mapper.AdminMapper;
import com.atguigu.crowd.funding.mapper.AuthMapper;
import com.atguigu.crowd.funding.mapper.RoleMapper;
import com.atguigu.crowd.funding.util.CrowdFundingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CrowdFundingUserDetailsService implements UserDetailsService {
    
    @Autowired
    private AdminMapper adminMapper;
    
    @Autowired
    private RoleMapper roleMapper;
    
    @Autowired
    private AuthMapper authMapper;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminExample adminExample = new AdminExample();
        
        adminExample.createCriteria().andLoginAcctEqualTo(username);
        
        List<Admin> list = adminMapper.selectByExample(adminExample);
    
        if (!CrowdFundingUtils.collectionEffective(list)) {
            return null;
        }
    
        Admin admin = list.get(0);
        String userPswd = admin.getUserPswd();
        
        List<GrantedAuthority> authorities = new ArrayList<>();
        Integer adminId = admin.getId();
        List<Role> roleList = roleMapper.selectAssignedRoleList(adminId);
    
        for (Role role : roleList) {
            String roleName = "ROLE_" + role.getName();
            authorities.add(new SimpleGrantedAuthority(roleName));
        }
        
        List<Auth> authList = authMapper.selectAuthListByAdminId(adminId);
        for (Auth auth : authList) {
            String authName = auth.getName();
            
            if (!CrowdFundingUtils.stringEffective(authName)) {
                continue;
            }
            
            authorities.add(new SimpleGrantedAuthority(authName));
        }
        
        return new SecurityAdmin(admin, authorities);
    }
}
