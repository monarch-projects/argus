package org.titan.argus.client.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.titan.argus.client.redis.RedisRepository;

/**
 * @author starboyate
 */
@ConditionalOnClass({RedisTemplate.class})
@Configuration
public class ArgusRedisAutoConfiguration {
	@Bean
	public RedisRepository redisRepository(RedisProperties properties) {
		return new RedisRepository(properties);
	}
}
