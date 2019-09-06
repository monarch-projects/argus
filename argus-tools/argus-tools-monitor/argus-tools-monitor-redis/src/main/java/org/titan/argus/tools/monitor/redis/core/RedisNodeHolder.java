package org.titan.argus.tools.monitor.redis.core;

import org.apache.commons.lang3.Validate;
import org.titan.argus.tools.monitor.redis.domain.RedisNode;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author starboyate
 */
public class RedisNodeHolder {
	private static final Map<Long, RedisNode> REDIS_NODE_CACHE = new ConcurrentHashMap<>();

	public static void add(RedisNode node) {
		Validate.notNull(node.getId());
		REDIS_NODE_CACHE.put(node.getId(), node);
	}
	public static RedisNode get(Long id) {
		return REDIS_NODE_CACHE.get(id);
	}

	public static Collection<RedisNode> getAll() {
		return REDIS_NODE_CACHE.values();
	}
}
