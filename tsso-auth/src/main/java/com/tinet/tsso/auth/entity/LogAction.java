package com.tinet.tsso.auth.entity;

import java.util.Date;

/**
 * 操作日志的实体类
 * 
 * @date 2017-08-30
 * @author lizy
 */
public class LogAction {
	private Integer id;// 操作日志的id

	private Integer userId;// 操作人id

	private String username;// 操作人的用户名

	private String fullName;// 操作人的全名

	private String operateDesc;// 操作描述

	private String operateObject;// 操作对象

	private Integer result;// 操作结果

	private Date operateTime;// 操作时间

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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName == null ? null : fullName.trim();
	}

	public String getOperateDesc() {
		return operateDesc;
	}

	public void setOperateDesc(String operateDesc) {
		this.operateDesc = operateDesc == null ? null : operateDesc.trim();
	}

	public String getOperateObject() {
		return operateObject;
	}

	public void setOperateObject(String operateObject) {
		this.operateObject = operateObject == null ? null : operateObject.trim();
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
}