package com.tinet.tsso.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 继承shiro的认证Filter，重写类中的某些方法，实现与cas的集成
 * 
 * @author 李政
 * @date 2017年8月3日
 */
public class CasFilter extends AuthenticatingFilter {

	private Logger logger = LoggerFactory.getLogger(getClass());

	// ticket的name
	private static final String TICKET_PARAMETER = "ticket";

	// 如果ticket验证失败了，跳转到的url
	private String failureUrl;

	/**
	 * 创建存有ticket的token
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String ticket = httpRequest.getParameter(TICKET_PARAMETER);
		return new CasToken(ticket);
	}

	/**
	 * 进行cas server端的登录，创建cas token ，并用这个token进行shiro的登录
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		return executeLogin(request, response);
	}

	/**
	 * 永远返回false来强制进行身份认证，用户理论上用不到这个
	 * 
	 * @param request
	 * @param response
	 * @param mappedValue
	 * @return <code>false</code>
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		return false;
	}

	/**
	 * 如果登录成功，重定向到指定好的url
	 * 
	 * @param token
	 *            登录结果
	 * @param subject
	 *            当前用户
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		issueSuccessRedirect(request, response);
		return false;
	}

	/**
	 * 如果登录失败，重定向到cas的错误页 如果用户已经登录过了，重定向到success url
	 * 
	 * @param token
	 * @param ae
	 * @param request
	 * @param response
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException ae, ServletRequest request,
			ServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug("Authentication exception", ae);
		}
		// is user authenticated or in remember me mode ?
		Subject subject = getSubject(request, response);
		if (subject.isAuthenticated() || subject.isRemembered()) {
			try {
				issueSuccessRedirect(request, response);
			} catch (Exception e) {
				logger.error("Cannot redirect to the default success url", e);
			}
		} else {
			try {
				WebUtils.issueRedirect(request, response, failureUrl);
			} catch (IOException e) {
				logger.error("Cannot redirect to failure url : {}", failureUrl, e);
			}
		}
		return false;
	}

	public void setFailureUrl(String failureUrl) {
		this.failureUrl = failureUrl;
	}

}
