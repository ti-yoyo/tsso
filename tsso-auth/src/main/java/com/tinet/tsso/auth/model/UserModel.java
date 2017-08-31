package com.tinet.tsso.auth.model;

import java.util.Date;
import java.util.List;

import com.tinet.tsso.auth.entity.Department;
import com.tinet.tsso.auth.entity.Role;

public class UserModel {
	private Integer id;// 用户的id

	private String username;// 用户账号

	private String password;// 用户密码

	private String fullName;// 用户的全名

	private List<Integer> departmentIds;// 用户 的部门id

	private String email;// 用户的邮箱
	
	private String mobile;// 用户的手机号

	private Integer status;// 用户的状态

	private Date createTime;// 用户的创建时间
	
	private Integer parentId;// 所属直销经理

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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName == null ? null : fullName.trim();
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

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public List<Integer> getDepartmentIds() {
		return departmentIds;
	}

	public void setDepartmentIds(List<Integer> departmentIds) {
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

	public List<Department> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<Department> departmentList) {
		this.departmentList = departmentList;
	}

}