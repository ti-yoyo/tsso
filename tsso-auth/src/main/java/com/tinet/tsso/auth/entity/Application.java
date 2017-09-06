package com.tinet.tsso.auth.entity;

import java.util.Date;

/**
 * 应用的实体类
 * 
 * @date 2017-08-14
 * @author lizy
 */
public class Application {
	private Integer id;// 应用的id

	private String key;// 应用标识

	private String name;// 应用名

	private Integer status;// 应用状态

	private String domainName;// 应用的域名

	private Date createTime;// 应用的创建时间

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	@Override
	public String toString() {
		return "Application [id=" + id + ", key=" + key + ", name=" + name + ", status=" + status + ", domainName="
				+ domainName + ", createTime=" + createTime + "]";
	}

}