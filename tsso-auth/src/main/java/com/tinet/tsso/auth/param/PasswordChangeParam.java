package com.tinet.tsso.auth.param;

/**
 * 修改密码的参数
 * 
 * @date 2017-08-28
 * @author lizy
 */
public class PasswordChangeParam {

	private String password;// 密码

	private String newPassword;//新密码

	private String reNewPassword;//确认新密码

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getReNewPassword() {
		return reNewPassword;
	}

	public void setReNewPassword(String reNewPassword) {
		this.reNewPassword = reNewPassword;
	}
	
}
