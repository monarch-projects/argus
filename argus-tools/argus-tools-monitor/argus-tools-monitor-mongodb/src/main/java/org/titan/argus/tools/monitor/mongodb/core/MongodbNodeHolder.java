package org.titan.argus.tools.monitor.mongodb.core;

import org.titan.argus.tools.monitor.mongodb.domain.MongodbNode;

import java.util.HashSet;
import java.util.Set;

/**
 * @author starboyate
 */
public class MongodbNodeHolder {
	private static final Set<MongodbNode> MONGODB_NODES_CACHE = new HashSet<>();

	public static void add(MongodbNode node) {
		MONGODB_NODES_CACHE.add(node);
	}
	public static Set<MongodbNode> get() {return MONGODB_NODES_CACHE;}

}
