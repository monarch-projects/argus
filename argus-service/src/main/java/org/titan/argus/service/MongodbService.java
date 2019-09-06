package org.titan.argus.service;

import org.titan.argus.tools.monitor.mongodb.domain.*;

import java.util.Collection;
import java.util.List;

/**
 * @author starboyate
 */
public interface MongodbService {
	Collection<MongodbNode> getAllMongodbNodeList();

	MongodbMetricInfo getMongodbMetricInfo(MongodbNode mongodbNode);

	MongodbGlobalLockInfo getMongodbGlobalLockInfo(MongodbNode mongodbNode);

	MongodbLogicalSessionRecordCacheInfo getMongodbSessionInfo(MongodbNode mongodbNode);

	MongodbReplInfo getMongodbReplInfo(MongodbNode mongodbNode);

	MongodbOSInfo getMongodbOSInfo(MongodbNode mongodbNode);

	MongodbStorageEngineInfo getMongodbStorageEngineInfo(MongodbNode mongodbNode);

	List<MongodbDataBase> getAllDataBaseInfo(MongodbNode node);

	String getCollectionSystemInfo(MongodbNode node, String dataBase, String collectionName);
}
