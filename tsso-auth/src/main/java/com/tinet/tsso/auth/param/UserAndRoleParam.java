package com.tinet.tsso.auth.param;

/**
 * 用户id和角色id的参数类
 * 
 * @date 2017-08-16
 * @author lizy
 */
public class UserAndRoleParam {

	private Integer userId;// 用户id
	private Integer roleId;// 角色id

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
}
