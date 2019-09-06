package org.titan.argus.service;

import org.titan.argus.tools.monitor.redis.domain.RedisMetricInfo;
import org.titan.argus.tools.monitor.redis.domain.RedisNode;
import org.titan.argus.tools.monitor.redis.domain.RedisNodeMetadataInfo;
import org.titan.argus.tools.monitor.redis.domain.RedisNodeSimpleInfo;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author starboyate
 */
public interface RedisService {
	RedisNodeSimpleInfo getRedisNodeSimpleInfo(RedisNode redisNode);

	List<RedisNodeMetadataInfo> getAllRedisMetadataInfo(RedisNode redisNode);

	Collection<RedisNode> getRedisNodeList();

	RedisMetricInfo getRedisMetricInfo(RedisNode redisNode);

	Object getRedisConfig(RedisNode redisNode);

	Map<String, String> setConfig(RedisNode redisNode, Map<String, String> params);
}
