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
import com.tinet.tsso.auth.param.LogActionParam;
import com.tinet.tsso.auth.service.LogActionService;
import com.tinet.tsso.auth.util.Page;

/**
 * 插入日志的实现
 *
 * @author lizy
 * @date 2017-08-24
 */
@Service
public class LogActionServiceImpl extends BaseServiceImp<LogAction, Integer> implements LogActionService {

	@Autowired
	private LogActionMapper logActionMapper;

	@Autowired
	private UserMapper userMapper;

	@Override
	public void addLogAction(String operateDesc, String operateObject, Integer result) {
/*		// 获取用户信息
		Subject subject = SecurityUtils.getSubject();
		List<Object> principals = subject.getPrincipals().asList();
		addLogAction(principals.get(0).toString(), operateDesc, operateObject, result);*/

	}

	/**
	 * 添加日志的方法
	 * 
	 * @param operateDesc
	 * @param operateObject
	 * @param result
	 * @param principals
	 */
	@Override
	public void addLogAction(String username, String operateDesc, String operateObject, Integer result) {
		// 获取用户
		List<User> userList = userMapper.selectByUsername(username);
		User user = userList.get(0);
		// 设置日志信息
		LogAction logAction = new LogAction();
		logAction.setUsername(username);
		logAction.setUserId(user.getId() == null ? 0 : user.getId());
		logAction.setFullName(user.getFullName());
		logAction.setOperateDesc(operateDesc);
		logAction.setOperateObject(operateObject);
		logAction.setResult(result);
		logAction.setOperateTime(new Date());
		// 插入日志
		logActionMapper.insertSelective(logAction);
	}

	/**
	 * 查询日志的实现
	 *
	 * @param logActionParam
	 *            查询的参数
	 * @return
	 */
	@Override
	public Page<LogAction> selectByParam(LogActionParam logActionParam) {
		if (logActionParam.getStart() == null) {
			logActionParam.setStart(0);
		}
		if (logActionParam.getLimit() == null) {
			logActionParam.setLimit(10);
		}
		List<LogAction> logActionList = logActionMapper.selectByParam(logActionParam);
		Integer logActionCount = logActionMapper.selectCountByParam(logActionParam);

		return new Page<LogAction>(logActionCount, logActionList);
	}
}
