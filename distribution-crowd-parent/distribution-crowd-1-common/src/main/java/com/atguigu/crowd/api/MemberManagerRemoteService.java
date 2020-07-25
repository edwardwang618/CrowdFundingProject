package com.atguigu.crowd.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.crowd.entity.ResultEntity;
import com.atguigu.crowd.entity.po.MemberLaunchInfoPO;
import com.atguigu.crowd.entity.vo.MemberSignSuccessVO;
import com.atguigu.crowd.entity.vo.MemberVO;

@FeignClient("member-manager")
public interface MemberManagerRemoteService {
    
    @RequestMapping("/retrieve/member/launch/info/by/member/token")
    ResultEntity<MemberLaunchInfoPO> retrieveMemberLaunchInfoByMemberToken(@RequestParam("token") String token);
    
    @RequestMapping("/member/manager/logout")
    ResultEntity<String> logout(@RequestParam("token") String token);
    
    @RequestMapping("/member/manager/login")
    ResultEntity<MemberSignSuccessVO> login(
            @RequestParam("loginAcct") String loginAcct,
            @RequestParam("userPswd") String userPswd);
    
    @RequestMapping("/member/manager/register")
    ResultEntity<String> register(@RequestBody MemberVO memberVO);
    
    @RequestMapping("/member/manager/send/code")
    ResultEntity<String> sendCode(@RequestParam("phoneNum") String phoneNum);
}
