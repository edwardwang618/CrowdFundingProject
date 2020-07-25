package com.atguigu.crowd.funding.config;

import com.atguigu.crowd.funding.entity.Admin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class SecurityAdmin extends User {
    
    private static final long serialVersionUID = 1L;
    
    private Admin originalAdmin;
    
    public SecurityAdmin(Admin originalAdmin, Collection<? extends GrantedAuthority> authorities) {
        super(originalAdmin.getLoginAcct(), originalAdmin.getUserPswd(), authorities);
        
        this.originalAdmin = originalAdmin;
    }
    
    public Admin getOriginalAdmin() {
        return originalAdmin;
    }
}
