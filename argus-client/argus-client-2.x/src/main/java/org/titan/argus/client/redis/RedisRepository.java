package org.titan.argus.client.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

import java.util.*;

/**
 * @author starboyate
 */
public class RedisRepository {
	private static final Logger logger = LoggerFactory.getLogger(RedisRepository.class);

	private final RedisProperties properties;

	public RedisRepository(RedisProperties properties) {
		this.properties = properties;
	}

	public RedisNode getRedisNode() {
		RedisNode redisNode = null;
		if (null != this.properties.getCluster() && !this.properties.getCluster().getNodes().isEmpty()) {
			redisNode = splitStringMapToRedisNode(this.properties.getCluster().getNodes());
			logger.info("redis mode is cluster, node is: {}", redisNode);
		} else if (null != this.properties.getSentinel() && !this.properties.getSentinel().getNodes().isEmpty()) {
			redisNode = splitStringMapToRedisNode(this.properties.getSentinel().getNodes());
			logger.info("redis mode is sentinel, node is: {}", redisNode);
		} else {
			redisNode = RedisNode.builder().host(this.properties.getHost()).port(this.properties.getPort()).build();
			logger.info("redis mode is standalone, node is: {}", redisNode);
		}
		redisNode.setPassword(this.properties.getPassword());
		return redisNode;
	}


	private RedisNode splitStringMapToRedisNode(List<String> list) {
		return list.stream()
				.map(item -> RedisNode.builder()
						.host(item.substring(0, item.lastIndexOf(":")))
						.port(Integer.valueOf(item.substring(item.lastIndexOf(":") + 1)))
						.build())
				.findFirst().orElse(null);
	}

}
