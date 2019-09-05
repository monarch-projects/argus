package org.titan.argus.example.redis;

import java.util.HashSet;
import java.util.Set;

/**
 * @author starboyate
 */
public class RedisNodeInfoHolder {
	private static final Set<RedisNodeInfo> set = new HashSet<>();

	public static void add(RedisNodeInfo redisNodeInfo) {
		set.add(redisNodeInfo);
	}
	public static Set<RedisNodeInfo> get() {return set;}
}
