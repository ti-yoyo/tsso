package com.tinet.tsso.auth.service.impl;

import com.tinet.tsso.auth.dao.LogLoginMapper;
import com.tinet.tsso.auth.entity.LogLogin;
import com.tinet.tsso.auth.param.LogLoginParam;
import com.tinet.tsso.auth.service.LogLoginService;
import com.tinet.tsso.auth.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lizy
 * @date 2017-08-25
 */
@Service
public class LogLoginServiceImpl implements LogLoginService {

	@Autowired
	private LogLoginMapper logLoginMapper;

	/**
	 * 按照参数查询登录日志
	 */
	@Override
	public Page<LogLogin> selectByParam(LogLoginParam logLoginParam) {
		if (logLoginParam.getStart() == null) {
			logLoginParam.setStart(0);
		}
		if (logLoginParam.getLimit() == null) {
			logLoginParam.setLimit(10);
		}
		List<LogLogin> logLoginList = logLoginMapper.selectByParam(logLoginParam);
		Integer logLoginCount = logLoginMapper.selectCountByParam(logLoginParam);
		return new Page<LogLogin>(logLoginCount, logLoginList);
	}
}
