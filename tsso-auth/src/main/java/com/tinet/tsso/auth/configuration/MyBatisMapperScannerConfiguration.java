package com.tinet.tsso.auth.configuration;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatisMapperScannerConfiguration
 * 
 * @date 2017-07-28
 * @author wangll
 */
@Configuration
@AutoConfigureAfter(MyBatisConfiguration.class)
public class MyBatisMapperScannerConfiguration {
	private static final String SQL_SESSION_FACTORY_BEAN_NAME = "sqlSessionFactory";
	private static final String MYBATIS_BASE_PACKAGE = "com.tinet.tsso.auth.dao";

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer configurer = new MapperScannerConfigurer();
		configurer.setSqlSessionFactoryBeanName(SQL_SESSION_FACTORY_BEAN_NAME);
		configurer.setBasePackage(MYBATIS_BASE_PACKAGE);
		return configurer;
	}
}
