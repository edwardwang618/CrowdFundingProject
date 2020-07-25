package com.atguigu.crowd.funding.handler;

import com.atguigu.crowd.funding.entity.Menu;
import com.atguigu.crowd.funding.entity.ResultEntity;
import com.atguigu.crowd.funding.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MenuHandler {
    
    @Autowired
    private MenuService menuService;
    
    @RequestMapping("/menu/update")
    public ResultEntity<String> updateMenu(Menu menu) {
        menuService.updateMenu(menu);
        return ResultEntity.successWithoutData();
    }
    
    @RequestMapping("/menu/get/{menuId}")
    public ResultEntity<Menu> getMenuById(@PathVariable("menuId") Integer menuId) {
        Menu menu = menuService.getMenuById(menuId);
        
        return ResultEntity.successWithData(menu);
    }
    
    @RequestMapping("/menu/save")
    public ResultEntity<String> saveMenu(Menu menu) {
        menuService.saveMenu(menu);
        
        return ResultEntity.successWithoutData();
    }
    
    @RequestMapping("/menu/get/whole/tree")
    public ResultEntity<Menu> getWholeTree() {
        
        List<Menu> menuList =  menuService.getAll();
        
        Map<Integer, Menu> menuMap = new HashMap<>();
    
        for (Menu menu : menuList) {
            Integer id = menu.getId();
            menuMap.put(id, menu);
        }
        
        Menu rootNode = null;
    
        for (Menu menu : menuList) {
            Integer pid = menu.getPid();
            
            if (pid == null) {
                rootNode = menu;
                continue;
            }
    
            Menu father = menuMap.get(pid);
            
            father.getChildren().add(menu);
        }
        
        return ResultEntity.successWithData(rootNode);
    }
}
