package com.tinet.tsso.shiro;

import org.apache.shiro.authc.RememberMeAuthenticationToken;

/**
 * cas登录成功后的结果
 * 
 * @author 李政
 * @date 2017年8月3日
 */
public class CasToken implements RememberMeAuthenticationToken {

	private static final long serialVersionUID = -2336605762747974275L;

	// CAS server 返回的Ticket
	private String ticket = null;

	private String userId = null;

	// 用户是否在remember me 模式下
	private boolean isRememberMe = false;

	public CasToken(String ticket) {
		this.ticket = ticket;
	}

	public Object getPrincipal() {
		return userId;
	}

	public Object getCredentials() {
		return ticket;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isRememberMe() {
		return isRememberMe;
	}

	public void setRememberMe(boolean isRememberMe) {
		this.isRememberMe = isRememberMe;
	}
}
