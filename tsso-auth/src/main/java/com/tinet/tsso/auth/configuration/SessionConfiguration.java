package com.tinet.tsso.auth.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.SessionRepositoryFilter;

/**
 * 
 * spring-session配置
 * 
 * @date 2017-07-28
 * @author wangll
 */
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 36000)
public class SessionConfiguration {

	private static final String SPRING_SESSION_URL_PATTERN = "/api/*";

	@Value("${spring.session.cookie.name}")
	private String cookieName;
	@Value("${spring.session.cookie.path}")
	private String cookiePath;
	@Value("${spring.session.cookie.only.http}")
	private Boolean onlyHttp;
	@Value("${spring.session.domain.name}")
	private String domainName;

	@Bean
	public static ConfigureRedisAction configureRedisAction() {
		return ConfigureRedisAction.NO_OP;
	}

	@Bean
	public CookieSerializer defaultCookieSerializer() {

		DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
		cookieSerializer.setCookieName(cookieName);
		cookieSerializer.setCookiePath(cookiePath);
		cookieSerializer.setUseHttpOnlyCookie(onlyHttp);
		cookieSerializer.setDomainName(domainName);

		return cookieSerializer;
	}

	@Bean("springSessionDefaultRedisSerializer")
	public RedisSerializer<Object> redisSerializer() {
		return new GenericJackson2JsonRedisSerializer();
	}

	@Bean
	public FilterRegistrationBean springSessionFilterRegistration(SessionRepositoryFilter<?> sessionRepositoryFilter) {

		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(sessionRepositoryFilter);
		registration.addUrlPatterns(SPRING_SESSION_URL_PATTERN);

		return registration;
	}
}
