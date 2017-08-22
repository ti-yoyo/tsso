package com.tinet.tsso.auth.configuration;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * 邮箱的配置类
 * 
 * @date 2017-08-18
 * @author lizy
 */
@Configuration
public class MailSenderConfiguration {
	
	@Value("${mail.host}")
	private String host;
	
	@Value("${mail.port}")
	private Integer port;
	
	@Value("${mail.protocol}")
	private String protocol;
	
	@Value("${mail.username}")
	private String username;
	
	@Value("${mail.password}")
	private String password;
	
	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(host);
		mailSender.setPort(port);
		mailSender.setProtocol(protocol);
		mailSender.setUsername(username);
		mailSender.setPassword(password);
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", true);
		javaMailProperties.put("mail.smtp.ssl.enable", false);
		
		mailSender.setJavaMailProperties(javaMailProperties);
		
		return mailSender;
	} 
}
