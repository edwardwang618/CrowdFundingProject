package com.atguigu.crowd.funding.service.api;

import com.atguigu.crowd.funding.entity.Auth;

import java.util.List;
import java.util.Map;

public interface AuthService {
    
    List<Auth> getAllAuth();
    
    List<Integer> getAssignedAuthIdList(Integer roleId);
    
    void updateRelationShipBetweenRoleAndAuth(Map<String, List<Integer>> assignDataMap);
}
