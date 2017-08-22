package com.tinet.tsso.auth.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;

@WebFilter(urlPatterns = "/api/*")
public class CrossDomainFilter implements Filter {

	@Value("${access.allow.origin}")
	private String accessAllowOrigin;

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		request.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");

		if (accessAllowOrigin != null) {
			res.addHeader("Access-Control-Allow-Origin", this.getProtocol(req) + accessAllowOrigin);
		} else {
			res.addHeader("Access-Control-Allow-Origin", "*");
		}
		res.addHeader("Access-Control-Allow-Credentials", "true");
		res.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept,X-Pagination");
		res.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		res.addHeader("Access-Control-Expose-Headers", "X-Pagination");

		// 解决跨域情况下 浏览器请求OPTIONS方法失败的问题
		if (req.getMethod().equals("OPTIONS")) {
			return;
		}

		chain.doFilter(request, res);
	}

	@Override
	public void destroy() {

	}

	/**
	 * 根据HttpServletRequest获取请求协议
	 * 
	 * @param request
	 * @return http:// 或 https://
	 */
	private String getProtocol(HttpServletRequest request) {
		String protocol = request.getHeader("X-Forwarded-Proto");
		if (protocol == null || protocol.length() == 0 || "unknown".equalsIgnoreCase(protocol)) {
			protocol = "http";
		}
		return protocol + "://";
	}

}
