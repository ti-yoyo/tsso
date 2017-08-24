package com.tinet.tsso.auth.service;

import java.util.HashMap;

/**
 * 设置的接口
 * 
 * @date 2017-08-23
 * @author lizy
 */
public interface SettingService {

	/**
	 * 初始化设置
	 */
	public void init();

	/**
	 * 修改配置
	 * 
	 * @param map
	 */
	public void changeSetting(HashMap<String, String> map);
}
