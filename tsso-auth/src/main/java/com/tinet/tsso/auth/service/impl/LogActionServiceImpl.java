package com.tinet.tsso.auth.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinet.tsso.auth.dao.LogActionMapper;
import com.tinet.tsso.auth.dao.UserMapper;
import com.tinet.tsso.auth.entity.LogAction;
import com.tinet.tsso.auth.entity.User;
import com.tinet.tsso.auth.service.LogActionService;

/**
 * 插入日志的实现
 * 
 * @date 2017-08-24
 * @author lizy
 */
@Service
public class LogActionServiceImpl extends BaseServiceImp<LogAction, Integer> implements LogActionService {

	@Autowired
	public LogActionMapper logActionMapper;

	@Autowired
	public UserMapper userMapper;

	@Override
	public void addLogAction(String operateDesc, String operateObject, Integer result) {
		// 获取用户信息
		Subject subject = SecurityUtils.getSubject();
		List<Object> principals = subject.getPrincipals().asList();

		// 获取用户
		List<User> userList = userMapper.selectByUsername(principals.get(0).toString());
		User user = userList.get(0);
		// 设置日志信息
		LogAction logAction = new LogAction();
		logAction.setUsername(principals.get(0).toString());
		logAction.setUserId(user.getId());
		logAction.setFullName(user.getFullName());
		logAction.setOperateDesc(operateDesc);
		logAction.setOperateObject(operateObject);
		logAction.setResult(result);
		logAction.setOperateTime(new Date());
		// 插入日志
		logActionMapper.insertSelective(logAction);

	}

}
