package com.tinet.tsso.auth.util;

import java.util.Hashtable;
import java.util.Map;

import com.tinet.tsso.auth.model.UsernameAndUuidModel;

/**
 * 存放新建用户的用户名和随机串
 * 
 * @date 2017-08-25
 * @author lizy
 */
public class ResetPasswordTmp {

	/**
	 * 存放重置密码的map
	 */
	
	public static Map<String, UsernameAndUuidModel> resetMap = new Hashtable<String, UsernameAndUuidModel>();

	public static Map<String, UsernameAndUuidModel> getResetMap() {
		return resetMap;
	}

	public static void setResetMap(Map<String, UsernameAndUuidModel> resetMap) {
		ResetPasswordTmp.resetMap = resetMap;
	}

}
