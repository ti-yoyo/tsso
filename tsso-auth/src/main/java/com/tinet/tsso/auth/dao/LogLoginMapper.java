package com.tinet.tsso.auth.dao;

import com.tinet.tsso.auth.entity.LogLogin;
import com.tinet.tsso.auth.param.LogLoginParam;

import java.util.List;

/**
 * @author lizy
 * @date 2017-8-25
 */
public interface LogLoginMapper {

	/**
	 * 根据参数查询登录日志
	 *
	 * @param logLoginParam
	 * @return
	 */
	List<LogLogin> selectByParam(LogLoginParam logLoginParam);

	/**
	 * 根据参数查询登录日志的数量
	 *
	 * @param logLoginParam
	 * @return
	 */
	Integer selectCountByParam(LogLoginParam logLoginParam);
}