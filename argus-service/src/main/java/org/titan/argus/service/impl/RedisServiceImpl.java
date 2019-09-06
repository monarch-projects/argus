package org.titan.argus.service.impl;

import org.springframework.stereotype.Service;
import org.titan.argus.service.RedisService;
import org.titan.argus.tools.monitor.redis.core.RedisNodeHolder;
import org.titan.argus.tools.monitor.redis.core.RedisRepository;
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
@Service
public class RedisServiceImpl implements RedisService {
	@Override
	public RedisNodeSimpleInfo getRedisNodeSimpleInfo(RedisNode redisNode) {
		return RedisRepository.getSimpleNodeInfo(redisNode);
	}

	@Override
	public List<RedisNodeMetadataInfo> getAllRedisMetadataInfo(RedisNode redisNode) {
		return RedisRepository.getNodeList(redisNode);
	}

	@Override
	public Collection<RedisNode> getRedisNodeList() {
		return RedisNodeHolder.getAll();
	}


	@Override
	public RedisMetricInfo getRedisMetricInfo(RedisNode redisNode) {
		return RedisRepository.getRedisMetricInfo(redisNode);
	}

	@Override
	public Object getRedisConfig(RedisNode redisNode) {
		return RedisRepository.getRedisConfig(redisNode);
	}

	@Override
	public Map<String, String> setConfig(RedisNode redisNode, Map<String, String> params) {
		return RedisRepository.setRedisConfig(redisNode, params);
	}
}
