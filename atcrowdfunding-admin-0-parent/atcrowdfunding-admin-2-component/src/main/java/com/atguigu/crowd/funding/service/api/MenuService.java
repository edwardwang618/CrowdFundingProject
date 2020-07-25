package com.atguigu.crowd.funding.service.api;

import com.atguigu.crowd.funding.entity.Menu;

import java.util.List;

public interface MenuService {
    
    List<Menu> getAll();
    
    void saveMenu(Menu menu);
    
    Menu getMenuById(Integer menuId);
    
    void updateMenu(Menu menu);
}
