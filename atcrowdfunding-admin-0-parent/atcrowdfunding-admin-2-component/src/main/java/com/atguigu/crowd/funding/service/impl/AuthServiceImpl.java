package com.atguigu.crowd.funding.service.impl;

import com.atguigu.crowd.funding.entity.Auth;
import com.atguigu.crowd.funding.entity.AuthExample;
import com.atguigu.crowd.funding.mapper.AuthMapper;
import com.atguigu.crowd.funding.service.api.AuthService;
import com.atguigu.crowd.funding.util.CrowdFundingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    
    @Autowired
    private AuthMapper authMapper;
    
    @Override
    public List<Auth> getAllAuth() {
        return authMapper.selectByExample(new AuthExample());
    }
    
    @Override
    public List<Integer> getAssignedAuthIdList(Integer roleId) {
        return authMapper.selectAssignedAuthIdList(roleId);
    }
    
    @Override
    public void updateRelationShipBetweenRoleAndAuth(Map<String, List<Integer>> assignDataMap) {
        List<Integer> roleIdList = assignDataMap.get("roleIdList");
        List<Integer> authIdList = assignDataMap.get("authIdList");
        
        Integer roleId = roleIdList.get(0);
        
        authMapper.deleteOldRelationship(roleId);
        
        if (CrowdFundingUtils.collectionEffective(authIdList)) {
            authMapper.insertNewRelationship(roleId, authIdList);
        }
    }
    
    
}
