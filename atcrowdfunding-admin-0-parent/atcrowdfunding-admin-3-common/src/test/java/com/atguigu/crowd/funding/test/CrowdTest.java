package com.atguigu.crowd.funding.test;

import com.atguigu.crowd.funding.util.CrowdFundingUtils;
import org.junit.Test;

public class CrowdTest {
    @Test
    public void testMD5() {
        String source = "123123";
        String target = CrowdFundingUtils.md5(source);
    
        System.out.println(target);
    }
}
