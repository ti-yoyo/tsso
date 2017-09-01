package com.tinet.tsso.auth.model;

/**
 * 所属经理
 * 
 * @date 2017-09-01
 * @author lizy
 */
public class Supervisor {
	
	private Integer id;
	
	private String username;// 用户账号
	
	private String fullName;// 用户的全名

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
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
