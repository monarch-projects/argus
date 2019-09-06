//package org.titan.argus.server.core;
//
//import com.google.common.collect.Sets;
//import org.titan.argus.model.entities.MongodbNode;
//
//import java.util.Set;
//
///**
// * @author starboyate
// */
//public class MiddleWareNodeHolder {
//	private static final Set<RedisNodeInfo> REDIS_NODE_INFO_SET;
//
//	private static final Set<MongodbNode> MONGODB_NODE_INFO_SET;
//
//
//	static {
//		REDIS_NODE_INFO_SET = Sets.newConcurrentHashSet();
//		MONGODB_NODE_INFO_SET = Sets.newConcurrentHashSet();
//	}
//	public static void addRedisNodeInfo(RedisNodeInfo info) {
//		REDIS_NODE_INFO_SET.add(info);
//	}
//
//	public static void addMongodbNodeInfo(MongodbNode info) {
//		MONGODB_NODE_INFO_SET.add(info);
//	}
//
//	public static Set<RedisNodeInfo> getRedisNodeInfoSet() {
//		return REDIS_NODE_INFO_SET;
//	}
//
//	public static Set<MongodbNode> getMongodbNodeInfoSet() {
//		return MONGODB_NODE_INFO_SET;
//	}
//
//	public static void clearRedisNode(){
//		REDIS_NODE_INFO_SET.clear();
//	}
//
//	public static void clearMongodbNode(){
//		MONGODB_NODE_INFO_SET.clear();
//	}
//}
