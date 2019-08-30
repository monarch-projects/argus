package org.titan.argus.plugin.redis.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.titan.argus.plugin.redis.core.RedisService;

/**
 * @author starboyate
 */
@ConditionalOnClass(name = "org.springframework.data.redis.core.RedisTemplate")
@Configuration
public class ArgusRedisAutoConfig {
	@Bean
	public RedisService redisService(RedisTemplate template, RedisProperties properties) {
		return new RedisService(template, properties);
	}
}
