package com.tinet.tsso.auth.model;

import java.util.Date;

/**
 * 返回登录日志信息的model
 * 
 * @date 2017-09-06
 * @author lizy
 */
public class LogLoginModel {
	private Integer id;// 在com_audit_trail中的id

	private Integer userId;// 登录用户的id

	private String fullName;// 登录用户的全名

	private String username;// 登录用户的用户名

	private String loginIp;// 登录的ip地址

	private Integer applicationId;// 登录应用的id

	private String applicationKey;// 登录应用的标识

	private String applicationName;// 登录应用的id

	private Integer result;// 登录结果

	private Date createTime;// 登录日志的创建时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp == null ? null : loginIp.trim();
	}

	public Integer getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName == null ? null : applicationName.trim();
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getApplicationKey() {
		return applicationKey;
	}

	public void setApplicationKey(String applicationKey) {
		this.applicationKey = applicationKey;
	}
}