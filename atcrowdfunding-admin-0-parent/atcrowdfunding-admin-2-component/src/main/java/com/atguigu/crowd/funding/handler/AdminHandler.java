package com.atguigu.crowd.funding.handler;

import com.atguigu.crowd.funding.entity.Admin;
import com.atguigu.crowd.funding.entity.ResultEntity;
import com.atguigu.crowd.funding.service.api.AdminService;
import com.atguigu.crowd.funding.util.CrowdFundingConstant;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AdminHandler {
    
    @Autowired
    private AdminService adminService;
    
    @ResponseBody
    @RequestMapping("/admin/batch/remove")
    public ResultEntity<String> batchRemove(@RequestBody List<Integer> adminIdList) {
        try {
            // System.out.println(10/0);
            
            adminService.batchRemove(adminIdList);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            return ResultEntity.failed(null, e.getMessage());
        }
    }
    
    @RequestMapping("/admin/query/for/search")
    public String queryForSearch(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                 @RequestParam(value = "keyword", defaultValue = "") String keyword,
                                 Model model) {
        PageInfo<Admin> pageInfo = adminService.queryForKeywordSearch(pageNum, pageSize, keyword);
        model.addAttribute(CrowdFundingConstant.ATTR_NAME_PAGE_INFO, pageInfo);
        return "admin-page";
    }
    
    @RequestMapping("/admin/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        
        return "redirect:/index.html";
    }
    
    @RequestMapping("/admin/do/login")
    public String doLogin(@RequestParam("loginAcct") String loginAcct,
                          @RequestParam("userPswd") String userPswd,
                          Model model,
                          HttpSession session) {
        Admin admin = adminService.login(loginAcct, userPswd);
        
        if (admin == null) {
            model.addAttribute(CrowdFundingConstant.ATTR_NAME_MESSAGE, CrowdFundingConstant.MESSAGE_LOGIN_FAILED);
            return "admin-login";
        }
        
        session.setAttribute(CrowdFundingConstant.ATTR_NAME_LOGIN_ADMIN, admin);
        
        return "redirect:/admin/to/main/page.html";
    }
    
    @RequestMapping("/admin/get/all")
    public String getAll(Model model) {
        List<Admin> list = adminService.getAll();
        model.addAttribute("list", list);
        return "admin-target";
    }
}
