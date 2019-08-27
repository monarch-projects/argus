package org.titan.argus.plugin.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author starboyate
 */
@Configuration
public class ArgusRedisAutoConfig {
	@Autowired
	private RedisTemplate redisTemplate;


}
