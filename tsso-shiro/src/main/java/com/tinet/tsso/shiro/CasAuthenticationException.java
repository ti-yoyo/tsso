package com.tinet.tsso.shiro;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 自定义的要抛出的异常
 * 
 * @author 李政
 * @date 2017年8月3日
 */

@SuppressWarnings("serial")
public class CasAuthenticationException extends AuthenticationException {

	public CasAuthenticationException() {
		super();
	}

	public CasAuthenticationException(String message) {
		super(message);
	}

	public CasAuthenticationException(Throwable cause) {
		super(cause);
	}

	public CasAuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}
}
