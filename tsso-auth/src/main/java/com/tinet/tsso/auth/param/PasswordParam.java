package com.tinet.tsso.auth.param;

/**
 * 对于密码的param
 * 
 * @date 2017-08-18
 * @author lizy
 */
public class PasswordParam {

	private String password;//密码
	
	private String repassword;//确认密码
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
}
