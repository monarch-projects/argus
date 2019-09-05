package org.titan.argus.tools.monitor.redis.core;

import org.titan.argus.tools.monitor.redis.domain.RedisNode;

import java.util.HashSet;
import java.util.Set;

/**
 * @author starboyate
 */
public class RedisNodeHolder {
	private static final Set<RedisNode> REDIS_NODE_CACHE = new HashSet<>();

	public static void add(RedisNode node) {
		REDIS_NODE_CACHE.add(node);
	}
	public static Set<RedisNode> get() {return REDIS_NODE_CACHE;}
}
