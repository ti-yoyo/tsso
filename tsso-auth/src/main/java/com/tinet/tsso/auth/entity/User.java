package com.tinet.tsso.auth.entity;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.tinet.tsso.auth.model.Supervisor;
import com.tinet.tsso.auth.util.DateFormatUtil;

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

	private Integer[] departmentIds;// 用户 的部门id

	private String email;// 用户的邮箱

	private String mobile;// 用户的手机号

	private Integer status;// 用户的状态
	
	private Date createTime;// 用户的创建时间

	private Integer parentId;// 直属经理id
	
	private Supervisor supervisor;//直属经理

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


	public Integer[] getDepartmentIds() {
		return departmentIds;
	}

	public void setDepartmentIds(Integer[] departmentIds) {
		this.departmentIds = departmentIds;
	}


	public Supervisor getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}

	@Override
	public String toString() {
		
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", passwordSalt=" + passwordSalt
				+ ", fullName=" + fullName + ", departmentIds=" + Arrays.toString(departmentIds) + ", email=" + email + ", mobile="
				+ mobile + ", status=" + status + ", createTime=" + DateFormatUtil.dataFormat(createTime) + ", parentId=" + parentId + "]";
	}

}