package com.tinet.tsso.auth.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinet.tsso.auth.dao.ComAuditTrailMapper;
import com.tinet.tsso.auth.entity.ComAuditTrail;
import com.tinet.tsso.auth.model.LogLoginModel;
import com.tinet.tsso.auth.param.LogLoginParam;
import com.tinet.tsso.auth.service.LogLoginService;
import com.tinet.tsso.auth.util.Page;

/**
 * 登录日志的service
 * 
 * @date 2017-09-06
 * @author lizy
 */
@Service
public class LogLoginServiceImpl implements LogLoginService{

	@Autowired
	private ComAuditTrailMapper comAuditTrailMapper;
	
	@Override
	public Page<LogLoginModel> selectByParam(LogLoginParam logLoginParam) {
		Integer count = comAuditTrailMapper.selectCountByParam(logLoginParam);
		List<ComAuditTrail> LoginAngLogoutList = comAuditTrailMapper.selectByParam(logLoginParam);
		List<LogLoginModel> pageData =new ArrayList<LogLoginModel>();
		for (ComAuditTrail comAuditTrail:LoginAngLogoutList) {
			LogLoginModel logLoginModel =new LogLoginModel();
			logLoginModel.setApplicationId(comAuditTrail.getApplication().getId());
			logLoginModel.setApplicationKey(comAuditTrail.getApplication().getKey());
			logLoginModel.setApplicationName(comAuditTrail.getApplication().getName());
			logLoginModel.setCreateTime(comAuditTrail.getAudDate());
			logLoginModel.setId(comAuditTrail.getId().intValue());
			logLoginModel.setLoginIp(comAuditTrail.getAudClientIp());
			logLoginModel.setResult(comAuditTrail.getAudAction().equals("AUTHENTICATION_SUCCESS")?1:0);
			logLoginModel.setUserId(comAuditTrail.getUser().getId());
			logLoginModel.setUsername(comAuditTrail.getAudUser());
			logLoginModel.setFullName(comAuditTrail.getUser().getFullName());
			pageData.add(logLoginModel);
		}
		return new Page<LogLoginModel>(count, pageData);
	}

}
