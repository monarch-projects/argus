package org.titan.argus.plugin.redis.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.titan.argus.plugin.redis.core.RedisService;

/**
 * @author starboyate
 */
@ConditionalOnClass({RedisTemplate.class})
@Configuration
public class ArgusRedisAutoConfig {
    @Bean
    public RedisService redisService(@Qualifier("redisTemplate") RedisTemplate redisTemplate, RedisProperties properties) {
        return new RedisService(redisTemplate, properties);
    }
}
