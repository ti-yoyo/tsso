package com.tinet.tsso.auth.service;

import com.tinet.tsso.auth.entity.LogAction;

/**
 * 日志的接口
 * 
 * @date 2017-08-24
 * @author lizy
 */
public interface LogActionService extends BaseService<LogAction, Integer> {

	public void addLogAction(String operateDesc,String operateObject,Integer result);
}
