package com.atguigu.crowd.handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.atguigu.crowd.api.MemberManagerRemoteService;
import com.atguigu.crowd.api.ProjectOperationRemoteService;
import com.atguigu.crowd.entity.ResultEntity;
import com.atguigu.crowd.entity.po.MemberLaunchInfoPO;
import com.atguigu.crowd.entity.vo.MemberSignSuccessVO;
import com.atguigu.crowd.entity.vo.ProjectVO;
import com.atguigu.crowd.util.CrowdConstant;
import com.atguigu.crowd.util.CrowdUtils;

@Controller
public class ProjectHandler {
	
	@Autowired
	private ProjectOperationRemoteService projectOperationRemoteService;
	
	@Autowired
	private MemberManagerRemoteService memberManagerRemoteService;
	
	@Value(value="${oss.project.parent.folder}")
	private String ossProjectParentFolder;

	@Value(value="${oss.endpoint}")
	private String endpoint;

	@Value(value="${oss.accessKeyId}")
	private String accessKeyId;

	@Value(value="${oss.accessKeySecret}")
	private String accessKeySecret;

	@Value(value="${oss.bucketName}")
	private String bucketName;
		
	@Value(value="${oss.bucket.domain}")
	private String bucketDomain;
	
	@ResponseBody
	@RequestMapping("/project/upload/detail/picture")
	public ResultEntity<String> uploadDetailPicture(
				HttpSession session,
				@RequestParam("detailPicture") List<MultipartFile> detailPictureList
			) throws IOException {
		
		// 登录检查
		MemberSignSuccessVO signVO = (MemberSignSuccessVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
		
		if(signVO == null) {
			
			return ResultEntity.failed(CrowdConstant.MESSAGE_ACCESS_DENIED);
		}
		
		// 遍历用户上传的文件
		if(!CrowdUtils.collectionEffectiveCheck(detailPictureList)) {
			return ResultEntity.failed(CrowdConstant.MESSAGE_UPLOAD_FILE_EMPTY);
		}
		
		List<String> detailPicturePathList = new ArrayList<>();
		
		for (MultipartFile detailPicture : detailPictureList) {
			boolean empty = detailPicture.isEmpty();
			
			if(empty) {
				continue ;
			}
			
			InputStream inputStream = detailPicture.getInputStream();
			
			String originalFileName = detailPicture.getOriginalFilename();
			
			String fileName = CrowdUtils.generateFileName(originalFileName);
			
			String folderName = CrowdUtils.generateFolderNameByDate(ossProjectParentFolder);
			
			CrowdUtils.uploadSingleFile(endpoint, accessKeyId, accessKeySecret, fileName, folderName, bucketName, inputStream);
			
			String detailPicturePath = bucketDomain + "/" + folderName + "/" + fileName;
			
			detailPicturePathList.add(detailPicturePath);
		}
		
		// 获取保存头图所需要的相关信息
		String memberSignToken = signVO.getToken();
		
		ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_INITED_PROJECT);
		
		String projectTempToken = projectVO.getProjectTempToken();
		
		return projectOperationRemoteService.saveDetailPicturePathList(memberSignToken, projectTempToken, detailPicturePathList);
		
	}
	
	@ResponseBody
	@RequestMapping("/project/upload/head/picture")
	public ResultEntity<String> uploadHeadPicture(
			HttpSession session,
			@RequestParam("headPicture") MultipartFile headPicture) throws IOException {
		
		// 登录检查
		MemberSignSuccessVO signVO = (MemberSignSuccessVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
		
		if(signVO == null) {
			
			return ResultEntity.failed(CrowdConstant.MESSAGE_ACCESS_DENIED);
		}
		
		// 排除上传文件为空的情况
		if(headPicture.isEmpty()) {
			return ResultEntity.failed(CrowdConstant.MESSAGE_UPLOAD_FILE_EMPTY);
		}
		
		// 准备工作
		String originalFileName = headPicture.getOriginalFilename();
		
		String fileName = CrowdUtils.generateFileName(originalFileName);
		
		String folderName = CrowdUtils.generateFolderNameByDate(ossProjectParentFolder);
		
		InputStream inputStream = headPicture.getInputStream();
		
		// 执行上传
		CrowdUtils.uploadSingleFile(endpoint, accessKeyId, accessKeySecret, fileName, folderName, bucketName, inputStream);
		
		// 拼接headerPicturePath
		String headerPicturePath = bucketDomain + "/" + folderName + "/" + fileName;
		
		// 获取保存头图所需要的相关信息
		String memberSignToken = signVO.getToken();
		
		ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_INITED_PROJECT);
		
		String projectTempToken = projectVO.getProjectTempToken();
		
		// 保存头图相关信息
		return projectOperationRemoteService.saveHeadPicturePath(memberSignToken, projectTempToken, headerPicturePath);
	}
	
	@RequestMapping("/project/agree/protocol")
	public String agreeProtocol(HttpSession session, Model model) {
		
		// 1.登录检查
		MemberSignSuccessVO signVO = (MemberSignSuccessVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
		
		if(signVO == null) {
			
			model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_ACCESS_DENIED);
			
			return "member-login";
		}
		
		// 2.从当前signVO对象中获取token
		String token = signVO.getToken();
		
		// 3.调用远程方法初始化Project
		ResultEntity<ProjectVO> resultEntity = projectOperationRemoteService.initCreation(token);
		
		String result = resultEntity.getResult();
		
		if(ResultEntity.FAILED.equals(result)) {
			throw new RuntimeException(resultEntity.getMessage());
		}
		
		// ※补充操作：将初始化项目的信息存入Session
		ProjectVO projectVO = resultEntity.getData();
		session.setAttribute(CrowdConstant.ATTR_NAME_INITED_PROJECT, projectVO);
		
		return "redirect:/project/to/create/project/page";
	}
	
	@RequestMapping("/project/to/create/project/page")
	public String toCreateProjectPage(HttpSession session, Model model) {
		
		// 1.获取当前登录的用户
		MemberSignSuccessVO signVO = (MemberSignSuccessVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
		
		// 2.检查signVO为null
		if(signVO == null) {
			model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_ACCESS_DENIED);
			return "member-login";
		}
		
		// 3.获取token数据
		String token = signVO.getToken();
		
		// 4.根据token查询MemberLaunchInfo信息
		ResultEntity<MemberLaunchInfoPO> resultEntity = memberManagerRemoteService.retrieveMemberLaunchInfoByMemberToken(token);
		
		// 5.检查结果
		String result = resultEntity.getResult();
		if(ResultEntity.FAILED.equals(result)) {
			throw new RuntimeException(resultEntity.getMessage());
		}
		
		// 6.获取查询结果
		MemberLaunchInfoPO memberLaunchInfoPO = resultEntity.getData();
		
		// 7.存入模型
		model.addAttribute("memberLaunchInfoPO", memberLaunchInfoPO);
		
		return "project-create";
	}

}
