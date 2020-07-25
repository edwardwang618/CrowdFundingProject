package com.atguigu.crowd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.crowd.entity.ResultEntity;
import com.atguigu.crowd.entity.po.MemberLaunchInfoPO;
import com.atguigu.crowd.entity.po.MemberPO;
import com.atguigu.crowd.service.api.MemberService;
import com.atguigu.crowd.util.CrowdConstant;
import com.atguigu.crowd.util.CrowdUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "在数据库中操作会员数据")
@RestController
public class MemberController {
    
    @Autowired
    private MemberService memberService;
    
    @ApiOperation(value = "根据memberId查询MemberLaunchInfo对象", httpMethod = "GET")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "memberId", value = "会员id", required = true)
            }
    )
    @RequestMapping("/retrieve/member/launch/info/po")
    public ResultEntity<MemberLaunchInfoPO> retrieveMemberLaunchInfoPO(@RequestParam("memberId") String memberId) {
        
        MemberLaunchInfoPO memberLaunchInfoPO = memberService.getMemberLaunchInfoPO(memberId);
        
        return ResultEntity.successWithData(memberLaunchInfoPO);
    }
    
    @RequestMapping("/retrieve/member/by/login/acct")
    public ResultEntity<MemberPO> retrieveMemberByLoginAcct(
            @RequestParam("loginAcct") String loginAcct) {
        
        try {
            MemberPO memberPO = memberService.getMemberByLoginAcct(loginAcct);
            
            return ResultEntity.successWithData(memberPO);
        } catch (Exception e) {
            e.printStackTrace();
            
            return ResultEntity.failed(e.getMessage());
        }
    }
    
    @RequestMapping("/retrieve/loign/acct/count")
    public ResultEntity<Integer> retrieveLoignAcctCount(
            @RequestParam("loginAcct") String loginAcct) {
        
        if (!CrowdUtils.strEffectiveCheck(loginAcct)) {
            return ResultEntity.failed(CrowdConstant.MESSAGE_LOGINACCT_INVALID);
        }
        
        try {
            int count = memberService.getLoginAcctCount(loginAcct);
            
            return ResultEntity.successWithData(count);
            
        } catch (Exception e) {
            e.printStackTrace();
            
            return ResultEntity.failed(e.getMessage());
        }
    }
    
    @RequestMapping("/save/member/remote")
    public ResultEntity<String> saveMemberRemote(@RequestBody MemberPO memberPO) {
        
        try {
            // 执行保存
            memberService.saveMemberPO(memberPO);
        } catch (Exception e) {
            e.printStackTrace();
            
            return ResultEntity.failed(e.getMessage());
        }
        
        return ResultEntity.successNoData();
        
    }
    
}
