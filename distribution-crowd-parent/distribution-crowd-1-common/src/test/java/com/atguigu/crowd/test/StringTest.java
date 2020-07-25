package com.atguigu.crowd.test;

import org.junit.Test;

import java.util.UUID;

public class StringTest {
    @Test
    public void testUUID() {
        UUID uuid = UUID.randomUUID();
    
        String s = uuid.toString();
        System.out.println(s);
    
        System.out.println(s.replaceAll("-", ""));
    }
}
