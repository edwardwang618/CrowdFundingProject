package com.atguigu.crowd.funding.service.api;

import com.atguigu.crowd.funding.entity.Role;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleService {
    PageInfo<Role> queryForKeywordWithPage(Integer pageNum, Integer pageSize, String keyword);
    
    List<Role> getRoleListByIdList(List<Integer> roleIdList);
    
    void batchRemove(List<Integer> roleIdList);
    
    void saveRole(String roleName);
    
    void updateRole(Role role);
    
    List<Role> getAssignedRoleList(Integer adminId);
    
    List<Role> getUnAssignedRoleList(Integer adminId);
    
    void updateRelationship(Integer adminId, List<Integer> roleIdList);
}
