package com.atguigu.dubbo.test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class DecodeTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String source = "dubbo%3A%2F%2F192.168.1.249%3A20880%2Fcom.atguigu.dubbo.api.EmployeeRemoteService%3Fanyhost%3Dtrue%26application%3Datguigu-dubbo-provider%26dubbo%3D2.5.5%26generic%3Dfalse%26interface%3Dcom.atguigu.dubbo.api.EmployeeRemoteService%26methods%3DgetEmployeeByConditionRemote%26pid%3D1635%26revision%3D1.0.0-SNAPSHOT%26side%3Dprovider%26timestamp%3D1594534203427";
        
        String decode = URLDecoder.decode(source, StandardCharsets.UTF_8);
        System.out.println(decode);
    }
}
