package com.atguigu.crowd.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crowd.entity.po.MemberConfirmInfoPO;
import com.atguigu.crowd.entity.po.MemberLaunchInfoPO;
import com.atguigu.crowd.entity.po.MemberLaunchInfoPOExample;
import com.atguigu.crowd.entity.po.ProjectItemPicPO;
import com.atguigu.crowd.entity.po.ProjectPO;
import com.atguigu.crowd.entity.po.ReturnPO;
import com.atguigu.crowd.entity.vo.MemberConfirmInfoVO;
import com.atguigu.crowd.entity.vo.MemberLauchInfoVO;
import com.atguigu.crowd.entity.vo.ProjectVO;
import com.atguigu.crowd.entity.vo.ReturnVO;
import com.atguigu.crowd.mapper.MemberConfirmInfoPOMapper;
import com.atguigu.crowd.mapper.MemberLaunchInfoPOMapper;
import com.atguigu.crowd.mapper.ProjectItemPicPOMapper;
import com.atguigu.crowd.mapper.ProjectPOMapper;
import com.atguigu.crowd.mapper.ReturnPOMapper;
import com.atguigu.crowd.mapper.TagPOMapper;
import com.atguigu.crowd.mapper.TypePOMapper;
import com.atguigu.crowd.service.api.ProjectService;
import com.atguigu.crowd.util.CrowdUtils;

@Service
@Transactional(readOnly=true)
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	private MemberConfirmInfoPOMapper memberConfirmInfoPOMapper;
	
	@Autowired
	private MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;
	
	@Autowired
	private ProjectItemPicPOMapper projectItemPicPOMapper;
	
	@Autowired
	private ProjectPOMapper projectPOMapper;
	
	@Autowired
	private ReturnPOMapper returnPOMapper;
	
	@Autowired
	private TagPOMapper tagPOMapper;
	
	@Autowired
	private TypePOMapper typePOMapper;

	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public void saveProject(ProjectVO projectVO, String memberId) {
		
		// 1.保存ProjectPO
		ProjectPO projectPO = new ProjectPO();
		BeanUtils.copyProperties(projectVO, projectPO);
		
		projectPO.setMemberid(Integer.parseInt(memberId));
		
		projectPOMapper.insert(projectPO);
		
		// 2.获取保存ProjectPO后得到的自增主键
		// 在ProjectPOMapper.xml文件中insert方法对应的标签中设置useGeneratedKeys="true" keyProperty="id"
		Integer projectId = projectPO.getId();
		
		// 3.保存typeIdList
		List<Integer> typeIdList = projectVO.getTypeIdList();
		if(CrowdUtils.collectionEffectiveCheck(typeIdList)) {
			typePOMapper.insertRelationshipBatch(projectId, typeIdList);
		}
		
		// 4.保存tagIdList
		List<Integer> tagIdList = projectVO.getTagIdList();
		if(CrowdUtils.collectionEffectiveCheck(tagIdList)) {
			tagPOMapper.insertRelationshipBatch(projectId, tagIdList);
		}
		
		// 5.保存detailPicturePathList
		// ①从VO对象中获取detailPicturePathList
		List<String> detailPicturePathList = projectVO.getDetailPicturePathList();
		if(CrowdUtils.collectionEffectiveCheck(detailPicturePathList)) {
			
			// ②创建一个空List集合，用来存储ProjectItemPicPO对象
			List<ProjectItemPicPO> projectItemPicPOList = new ArrayList<>();
			
			// ③遍历detailPicturePathList
			for (String detailPath : detailPicturePathList) {
				
				// ④创建projectItemPicPO对象
				ProjectItemPicPO projectItemPicPO = new ProjectItemPicPO(null, projectId, detailPath);
				
				projectItemPicPOList.add(projectItemPicPO);
			}
			
			// ⑤根据projectItemPicPOList执行批量保存
			projectItemPicPOMapper.insertBatch(projectItemPicPOList);
		}
		
		
		// 6.保存MemberLaunchInfoPO
		MemberLauchInfoVO memberLauchInfoVO = projectVO.getMemberLauchInfoVO();
		
		if(memberLauchInfoVO != null) {
			
			// ※※※※※※※※※※※补充功能※※※※※※※※※※※
			// 将旧的用户发起人信息删除
			MemberLaunchInfoPOExample example = new MemberLaunchInfoPOExample();
			example.createCriteria().andMemberidEqualTo(Integer.parseInt(memberId));
			memberLaunchInfoPOMapper.deleteByExample(example);
			// ※※※※※※※※※※※※※※※※※※※※※※※※※※
			
			MemberLaunchInfoPO memberLaunchInfoPO = new MemberLaunchInfoPO();
			BeanUtils.copyProperties(memberLauchInfoVO, memberLaunchInfoPO);
			
			memberLaunchInfoPO.setMemberid(Integer.parseInt(memberId));
			
			memberLaunchInfoPOMapper.insert(memberLaunchInfoPO);
		}
		
		// 7.根据ReturnVO的List保存ReturnPO
		List<ReturnVO> returnVOList = projectVO.getReturnVOList();
		
		if(CrowdUtils.collectionEffectiveCheck(returnVOList)) {
			
			List<ReturnPO> returnPOList = new ArrayList<>();
			
			for (ReturnVO returnVO : returnVOList) {
				
				ReturnPO returnPO = new ReturnPO();
				
				BeanUtils.copyProperties(returnVO, returnPO);
				
				returnPO.setProjectid(projectId);
				
				returnPOList.add(returnPO);
			}
			
			returnPOMapper.insertBatch(returnPOList);
			
		}
		
		// 8.保存MemberConfirmInfoPO
		MemberConfirmInfoVO memberConfirmInfoVO = projectVO.getMemberConfirmInfoVO();
		
		if(memberConfirmInfoVO != null) {
			MemberConfirmInfoPO memberConfirmInfoPO = new MemberConfirmInfoPO(null, Integer.parseInt(memberId), memberConfirmInfoVO.getPaynum(), memberConfirmInfoVO.getCardnum());
			memberConfirmInfoPOMapper.insert(memberConfirmInfoPO);
			
		}
		
	}

}
