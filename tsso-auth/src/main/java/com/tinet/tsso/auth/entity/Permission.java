package com.tinet.tsso.auth.entity;

import java.util.Date;

/**
 * 权限类
 * 
 * @date 2017-08-14
 * @author lizy
 */
public class Permission {
	private Integer id;// 权限id

	private String key;// 权限标识

	private String name;// 权限名

	private Integer applicationId; // 所属应用id

	private Date createTime;// 创建时间
	
	private Application application;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key == null ? null : key.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Integer getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	@Override
	public String toString() {
		return "Permission [id=" + id + ", key=" + key + ", name=" + name + ", applicationId=" + applicationId
				+ ", createTime=" + createTime + "]";
	}
	
}