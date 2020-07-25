package com.atguigu.crowd.funding.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    
    // 对应数据库表主键
    private Integer id;
    
    // 对应父节点id（如果pid为null，则说明当前节点是根节点）
    private Integer pid;
    
    // 节点名称
    private String name;
    
    // 节点对应的URL地址
    private String url;
    
    // 节点的图标
    private String icon;
    
    // 当前节点的子节点集合，设置默认值是为了避免组装节点时空指针异常
    private List<Menu> children = new ArrayList<>();
    
    // 控制节点展开还是折叠，设置为true是让整个树形菜单默认展开
    private Boolean open = true;
}