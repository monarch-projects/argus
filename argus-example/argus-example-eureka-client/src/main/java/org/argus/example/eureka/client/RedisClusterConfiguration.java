package org.argus.example.eureka.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisClusterConfiguration{

	@Bean
	public RedisTemplate<String,String> redisTemplate(RedisConnectionFactory redisConnectionfactory){
		RedisTemplate<String,String> redisTemplate=new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionfactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
}
