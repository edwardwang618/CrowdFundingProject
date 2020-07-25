package com.atguigu.security.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderTest {
    
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        CharSequence rawPassword = "123123";
        
        for (int i = 0; i < 10; i++) {
            String encodedPassword = encoder.encode(rawPassword);
            System.out.println(encodedPassword);
        }
        
        System.out.println();
        
        boolean matches = encoder.matches(rawPassword, "$2a$10$Y2Cq8ilT21ME.lvu6bwcPO/RMkU7ucAZpmFzx7GDTXK9KNxHyEM1e");
        System.out.println(matches);
    }
    
}
