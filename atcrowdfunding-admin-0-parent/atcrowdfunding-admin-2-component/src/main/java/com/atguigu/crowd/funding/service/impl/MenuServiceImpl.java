package com.atguigu.crowd.funding.service.impl;

import com.atguigu.crowd.funding.entity.Menu;
import com.atguigu.crowd.funding.entity.MenuExample;
import com.atguigu.crowd.funding.mapper.MenuMapper;
import com.atguigu.crowd.funding.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    
    @Autowired
    private MenuMapper menuMapper;
    
    @Override
    public List<Menu> getAll() {
        
        return menuMapper.selectByExample(new MenuExample());
    }
    
    @Override
    public void saveMenu(Menu menu) {
        menuMapper.insert(menu);
    }
    
    @Override
    public Menu getMenuById(Integer menuId) {
        return menuMapper.selectByPrimaryKey(menuId);
    }
    
    @Override
    public void updateMenu(Menu menu) {
        menuMapper.updateByPrimaryKey(menu);
    }
}
