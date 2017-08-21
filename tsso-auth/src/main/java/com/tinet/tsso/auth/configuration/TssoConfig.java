package com.tinet.tsso.auth.configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.Filter;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.tinet.tsso.shiro.CasFilter;
import com.tinet.tsso.shiro.CasRealm;
import com.tinet.tsso.shiro.CasSubjectFactory;

/**
 * Shiro集成CAS配置类
 * 
 * @author 李政
 * @date 2017年8月3日
 */
//@Configuration
public class TssoConfig {

	// CasServerUrlPrefix
	@Value("${tsso.cas.serverUrl}")
	private String casServerUrlPrefix;

	// 当前工程对外提供的服务地址
	@Value("${tsso.cas.clientUrl}")
	private String shiroServerUrlPrefix;

	// casFilter UrlPattern
	@Value("${tsso.cas.urlPattern}")
	private String casFilterUrlPattern;

	// Cas登录地址
	@Value("${tsso.cas.loginUrl}")
	private String loginUrl;

	/**
	 * 监听服务端发送的登出消息，用于实现单点登出功能
	 */
	@Bean
	public ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> singleSignOutHttpSessionListener() {
		ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> listener = new ServletListenerRegistrationBean<>();
		listener.setListener(new SingleSignOutHttpSessionListener());
		return listener;
	}

	/**
	 * 该过滤器用于实现单点登出功能，单点退出配置，一定要放在其他filter之前
	 */
	@Bean
	public FilterRegistrationBean singleSignOutFilter() {

		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();

		filterRegistration.setFilter(new SingleSignOutFilter());

		filterRegistration.addUrlPatterns("/logout");
		filterRegistration.addInitParameter("casServerUrlPrefix", casServerUrlPrefix);
		filterRegistration.addInitParameter("serverName", shiroServerUrlPrefix);

		return filterRegistration;
	}

	/**
	 * 自定义的Realm,用于shiro的验证
	 */
	@Bean(name = "casRealm")
	public CasRealm casRealm() {

		CasRealm realm = new CasRealm();

		// 这里的名字要和cas server端的配置文件里的名字对应，目前就是roles和permissions
		realm.setRoleAttributeNames("roles");
		realm.setPermissionAttributeNames("permissions");

		realm.setCasServerUrlPrefix(casServerUrlPrefix);
		realm.setCasService(shiroServerUrlPrefix + casFilterUrlPattern);

		return realm;
	}

	/**
	 * 注册SecurityManager
	 */
	@Bean(name = "securityManager")
	public DefaultWebSecurityManager defaultWebSecurityManager(CasRealm casRealm) {

		DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();

		// 指定Realm
		dwsm.setRealm(casRealm);
		// 指定 SubjectFactory
		dwsm.setSubjectFactory(new CasSubjectFactory());

		return dwsm;
	}

	/**
	 * CAS过滤器
	 */
	@Bean(name = "casFilter")
	public CasFilter casFilter() {

		CasFilter casFilter = new CasFilter();

		casFilter.setName("casFilter");
		// 登录失败后跳转的URL，也就是 Shiro 执行 CasRealm 的 doGetAuthenticationInfo
		casFilter.setFailureUrl(loginUrl);// 我们选择认证失败后再打开登录页面
		casFilter.setSuccessUrl("/api/user");

		return casFilter;
	}

	/**
	 * ShiroFilter配置
	 */
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilterFactoryBean(
			@Qualifier("securityManager") DefaultWebSecurityManager securityManager,
			@Qualifier("casFilter") CasFilter casFilter) {

		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		// 必须设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl(loginUrl);

		// 登录成功后要跳转的连接
		shiroFilterFactoryBean.setSuccessUrl("/api/user");

		// 添加casFilter到shiroFilter中
		Map<String, Filter> filters = new HashMap<>();
		filters.put("casFilter", casFilter);
		shiroFilterFactoryBean.setFilters(filters);

		// 配置访问权限
		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

		// shiro集成cas后，首先添加该规则
		filterChainDefinitionMap.put(casFilterUrlPattern, "casFilter");
		// 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
		filterChainDefinitionMap.put("/logout", "logout");


		 // 修改密码的链接不需要权限控制
		
		filterChainDefinitionMap.put("/api/password/forget_password", "anon");
		filterChainDefinitionMap.put("/api/password/change_password", "anon");

		// 所有url都需要验证
		filterChainDefinitionMap.put("/**", "authc");
		//
		filterChainDefinitionMap.put("/**", "roles[auth_admin]");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		return shiroFilterFactoryBean;

		// 默认过滤器(10个) 对应filterChainDefinitionMap里的value
		// anon -- org.apache.shiro.web.filter.authc.AnonymousFilter
		// authc -- org.apache.shiro.web.filter.authc.FormAuthenticationFilter
		// authcBasic--
		// org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter

		// perms --
		// org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter

		// port -- org.apache.shiro.web.filter.authz.PortFilter
		// rest -- org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter
		// roles -- org.apache.shiro.web.filter.authz.RolesAuthorizationFilter
		// ssl -- org.apache.shiro.web.filter.authz.SslFilter
		// user -- org.apache.shiro.web.filter.authc.UserFilter
		// logout -- org.apache.shiro.web.filter.authc.LogoutFilter
		//
		// anon:例子/admins/**=anon 没有参数，表示可以匿名使用。
		// authc:例如/admins/user/**=authc表示需要认证(登录)才能使用，没有参数

		// roles：例子/admins/user/**=roles[admin],参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，
		// 当有多个参数时，例如admins/user/**=roles["admin,guest"],每个参数通过才算通过，相当于hasAllRoles()方法。

		// perms：例子/admins/user/**=perms[user:add:*],参数可以写多个，多个时必须加上引号，
		// 并且参数之间用逗号分割，例如/admins/user/**=perms["user:add:*,user:modify:*"]，当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法。

		// rest：例子/admins/user/**=rest[user],根据请求的方法，相当于/admins/user/**=perms[user:method]
		// ,其中method为post，get，delete等。

		// port：例子/admins/user/**=port[8081],当请求的url的端口不是8081是跳转到schemal://serverName:8081?queryString,
		// 其中schmal是协议http或https等，serverName是你访问的host,8081是url配置里port的端口，queryString是你访问的url里的？后面的参数。

		// authcBasic：例如/admins/user/**=authcBasic没有参数表示httpBasic认证
		// ssl:例子/admins/user/**=ssl没有参数，表示安全的url请求，协议为https
		// user:例如/admins/user/**=user没有参数，表示必须存在用户，当登入操作时不做检查
	}

	/**
	 * 注册DelegatingFilterProxy（Shiro）
	 */
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {

		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();

		filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));

		// 该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
		filterRegistration.addInitParameter("targetFilterLifecycle", "true");
		filterRegistration.addUrlPatterns("/*");

		return filterRegistration;
	}

	/**
	 * 开启shiro aop注解支持
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	/**
	 * spring mvc处理全局异常的类，这里捕获shiro注解产生的未授权异常，跳转到403页面
	 * 
	 * @return
	 */
	@Bean
	public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
		Properties properties = new Properties();
		properties.put("org.apache.shiro.authz.AuthorizationException", "/403");
		simpleMappingExceptionResolver.setExceptionMappings(properties);
		return simpleMappingExceptionResolver;
	}
}
