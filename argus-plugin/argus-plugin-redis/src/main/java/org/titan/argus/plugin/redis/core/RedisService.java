package org.titan.argus.plugin.redis.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.RedisClientInfo;
import org.titan.argus.plugin.redis.entities.RedisNode;
import org.titan.argus.plugin.redis.entities.RedisNodeInfo;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author starboyate
 */
public class RedisService {
	private static final Logger logger = LoggerFactory.getLogger(RedisService.class);
	private final RedisTemplate template;

	private final RedisProperties properties;

	public RedisService(RedisTemplate template, RedisProperties properties) {
		this.template = template;
		this.properties = properties;
	}

	public RedisNodeInfo getRedisNodeInfo() {
		RedisNodeInfo info = new RedisNodeInfo();
		List<String> nodeList = null;
		if (null != this.properties.getCluster() && !this.properties.getCluster().getNodes().isEmpty()) {
			List<RedisNode> collect = splitStringMapToList(this.properties.getCluster().getNodes());
			info.setIsCluster(Boolean.TRUE).setNodeList(collect);
			logger.info("redis mode is cluster, redisInfo is: {}", info);
		} else if (null != this.properties.getSentinel() && !this.properties.getSentinel().getNodes().isEmpty()) {
			List<RedisNode> collect = splitStringMapToList(this.properties.getSentinel().getNodes());
			info.setIsSentinel(Boolean.TRUE).setNodeList(collect);
			logger.info("redis mode is sentinel, redisInfo is: {}", info);
		} else {
			info.setNodeList(Arrays.asList(RedisNode.builder().host(this.properties.getHost()).port(this.properties.getPort()).build()));
			logger.info("redis mode is standalone, redisInfo is: {}", info);
		}
		return info;
	}

	private List<RedisNode> splitStringMapToList(List<String> list) {
		return list.stream()
				.map(item -> RedisNode.builder()
						.host(item.substring(0, item.lastIndexOf(":")))
						.port(Integer.valueOf(item.substring(item.lastIndexOf(":") + 1)))
						.build())
				.collect(Collectors.toList());
	}

	public Object getRedisAllInfo() {
		RedisProperties.Cluster cluster = properties.getCluster();
		RedisProperties.Sentinel sentinel = properties.getSentinel();
		return this.template.execute((RedisCallback) con -> con.info());
	}

	public Object getRedisUsedMemoryInfo() {
		this.template.getClientList().forEach(item -> {
			RedisClientInfo info = (RedisClientInfo) item;
			System.out.println(info.getAddressPort());
		});
		return this.template.execute((RedisCallback) con -> con.info("memory").get("used_memory"));
	}

	public Map<Object, Object> getAllResources() {
		Set keys = this.template.keys("*");
		Map map = new HashMap(keys.size());
		keys.forEach(key -> {
			map.put(key, this.template.opsForValue().get(key));
		});
		return map;
	}

	public Object getRedisConfigArgs() {
		return this.template.execute((RedisCallback)con -> con.getConfig("*"));
	}

	public Object changeRedisConfigArgs(Map<String, String> map) {
		Map<String, String> temp = new HashMap<>(map);
		return this.template.execute((RedisCallback) con -> {
			map.forEach((k, v) -> {
				try {
					con.setConfig(k, v);
				} catch (Exception ex) {
					temp.remove(k);
				}
			});
			return temp;
		});
	}
}
