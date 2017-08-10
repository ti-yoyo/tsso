package com.tinet.tsso.auth.model;

/**
 * @date 2017-08-09
 * @author lizy
 */

public class RoleParam {
	
	private Integer id;//角色id
	
	private Integer start;//分页的起始位置
	
	private Integer limit;//分页大小
	
	private String name;//角色名
	
	private Integer userId;//用户id
	
	private Integer permissionId;//权限id

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}
	
}
