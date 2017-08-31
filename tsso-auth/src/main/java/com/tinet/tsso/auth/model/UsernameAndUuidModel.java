package com.tinet.tsso.auth.model;

import java.util.Date;

/**
 * 
 * 忘记密码的model
 * 
 * @date 2017-08-18
 * @author lizy
 */
public class UsernameAndUuidModel {

	 private String key;//找回密码的标识
	 
	 private Date date;//找回密码的日期

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	 
}
