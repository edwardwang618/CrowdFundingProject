package com.atguigu.crowd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crowd.entity.po.MemberLaunchInfoPO;
import com.atguigu.crowd.entity.po.MemberLaunchInfoPOExample;
import com.atguigu.crowd.entity.po.MemberPO;
import com.atguigu.crowd.entity.po.MemberPOExample;
import com.atguigu.crowd.mapper.MemberLaunchInfoPOMapper;
import com.atguigu.crowd.mapper.MemberPOMapper;
import com.atguigu.crowd.service.api.MemberService;
import com.atguigu.crowd.util.CrowdUtils;

@Service
@Transactional(readOnly=true)
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberPOMapper memberPOMapper;
	
	@Autowired
	private MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;

	@Override
	public int getLoginAcctCount(String loginAcct) {
		
		MemberPOExample example = new MemberPOExample();
		
		example.createCriteria().andLoginacctEqualTo(loginAcct);
		
		return memberPOMapper.countByExample(example);
	}

	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public void saveMemberPO(MemberPO memberPO) {
		memberPOMapper.insert(memberPO);
	}

	@Override
	public MemberPO getMemberByLoginAcct(String loginAcct) {
		
		MemberPOExample example = new MemberPOExample();
		
		example.createCriteria().andLoginacctEqualTo(loginAcct);
		
		List<MemberPO> list = memberPOMapper.selectByExample(example);
		
		if(CrowdUtils.collectionEffectiveCheck(list)) {
			return list.get(0);
		}
		
		return null;
	}

	@Override
	public MemberLaunchInfoPO getMemberLaunchInfoPO(String memberId) {
		
		MemberLaunchInfoPOExample example = new MemberLaunchInfoPOExample();
		
		example.createCriteria().andMemberidEqualTo(Integer.parseInt(memberId));
		
		List<MemberLaunchInfoPO> list = memberLaunchInfoPOMapper.selectByExample(example);
		
		if(CrowdUtils.collectionEffectiveCheck(list)) {
			return list.get(0);
		}else {
			return null;
		}
		
	}
	
}
