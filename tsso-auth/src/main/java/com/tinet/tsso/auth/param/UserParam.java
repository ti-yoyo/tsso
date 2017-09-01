package com.tinet.tsso.auth.param;

/**
 * 对于用户请求的参数
 * 
 * @date 2017-08-09
 * @author lizy
 */

public class UserParam {

	private Integer id;// 用户id

	private String password;// 用户密码


	private String email;// 邮箱

	private Integer status;// 状态

	private Integer start;// 分页的起始位置

	private Integer limit;// 分页大小

	private String username;// 账号

	private String fullName;// 全名

	private Integer roleId;// 角色id
	
	private Integer[] departmentIds;// 用户 的部门id
	
	private String mobile;// 用户的手机号
	
	private Integer parentId;// 所属直销经理

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer[] getDepartmentIds() {
		return departmentIds;
	}

	public void setDepartmentIds(Integer[] departmentIds) {
		this.departmentIds = departmentIds;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
}
