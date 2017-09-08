package com.tinet.tsso.auth.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 部门实体类
 * 
 * @date 2017-08-14
 * @author lizy
 */
@JsonInclude(Include.NON_NULL)
public class Department {
	private Integer id;// 部门的id

	private String name;// 部门名

	private Date createTime;// 部门的创建时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}