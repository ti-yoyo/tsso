package com.tinet.tsso.auth.entity;

import java.util.Date;
import java.util.List;

/**
 * 用户的entity
 * 
 * @date 2017-08-14
 * @author lizy
 */
public class User {
	private Integer id;// 用户的id

	private String username;// 用户账号

	private String password;// 用户密码

	private String passwordSalt;// 密码的盐

	private String fullName;// 用户的全名

	private List<Integer> departmentIds;// 用户 的部门id

	private String email;// 用户的邮箱

	private Integer status;// 用户的状态

	private Date createTime;// 用户的创建时间

	private List<Department> departmentList;// 用户所在部门
	
	private List<Role> roleList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getPasswordSalt() {
		return passwordSalt;
	}

	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt == null ? null : passwordSalt.trim();
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName == null ? null : fullName.trim();
	}


	public List<Integer> getDepartmentIds() {
		return departmentIds;
	}

	public void setDepartmentIds(List<Integer> departmentIds) {
		this.departmentIds = departmentIds;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
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
	
	
	public List<Department> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<Department> departmentList) {
		this.departmentList = departmentList;
	}
	
	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", passwordSalt=" + passwordSalt
				+ ", fullName=" + fullName + ", departmentIds=" + departmentIds + ", email=" + email + ", status="
				+ status + ", createTime=" + createTime + "]";
	}

}