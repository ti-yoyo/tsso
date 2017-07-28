package com.tinet.tsso.auth.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;
/**
 * redis configuration
 * @date 2017-07-28
 * @author wangll
 */
@Configuration
public class RedisConfiguration {
	@Value("${spring.redis.host}")
	private String hostName;
	@Value("${spring.redis.password}")
	private String password;
	@Value("${spring.redis.database}")
	private int database;
	@Value("${spring.redis.port}")
	private int port;
	@Value("${spring.redis.pool.max-idle}")
	private int maxIdle;
	@Value("${spring.redis.pool.min-idle}")
	private int minIdle;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
		connectionFactory.setHostName(hostName);
		connectionFactory.setPassword(password);
		connectionFactory.setDatabase(database);
		connectionFactory.setPort(port);
		;
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(maxIdle);
		poolConfig.setMinIdle(minIdle);
		connectionFactory.setPoolConfig(poolConfig);
		return connectionFactory;
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		redisTemplate.setKeySerializer(stringRedisSerializer);

		// 采用Json序列化
		GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
		redisTemplate.setHashValueSerializer(jsonRedisSerializer);
		redisTemplate.setValueSerializer(jsonRedisSerializer);

		redisTemplate.setConnectionFactory(connectionFactory);
		return redisTemplate;
	}
}
