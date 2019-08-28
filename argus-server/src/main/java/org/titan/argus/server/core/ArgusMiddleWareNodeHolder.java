package org.titan.argus.server.core;

import com.google.common.collect.Sets;
import org.titan.argus.model.entities.MongodbNodeInfo;
import org.titan.argus.model.entities.RedisNodeInfo;

import java.util.Set;

/**
 * @author starboyate
 */
public class ArgusMiddleWareNodeHolder {
	private static final Set<RedisNodeInfo> REDIS_NODE_INFO_SET;

	private static final Set<MongodbNodeInfo> MONGODB_NODE_INFO_SET;

	static {
		REDIS_NODE_INFO_SET = Sets.newConcurrentHashSet();
		MONGODB_NODE_INFO_SET = Sets.newConcurrentHashSet();
	}
	public static void addRedisNodeInfo(RedisNodeInfo info) {
		REDIS_NODE_INFO_SET.add(info);
	}

	public static void addMongodbNodeInfo(MongodbNodeInfo info) {
		MONGODB_NODE_INFO_SET.add(info);
	}
}
