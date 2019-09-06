package org.titan.argus.tools.monitor.mongodb.core;



import org.apache.commons.lang3.Validate;
import org.titan.argus.tools.monitor.mongodb.domain.MongodbNode;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author starboyate
 */
public class MongodbNodeHolder {
	private static final Map<Long, MongodbNode> MONGODB_NODES_CACHE = new ConcurrentHashMap<>();

	public static void add(MongodbNode node) {
		Validate.notNull(node.getId());
		MONGODB_NODES_CACHE.put(node.getId(), node);
	}
	public static MongodbNode get(Long id) {return MONGODB_NODES_CACHE.get(id);}

	public static Collection<MongodbNode> getAll() {
		return MONGODB_NODES_CACHE.values();
	}

}
