package org.titan.argus.service.impl;

import org.springframework.stereotype.Service;
import org.titan.argus.service.MongodbService;
import org.titan.argus.tools.monitor.mongodb.core.MongodbNodeHolder;
import org.titan.argus.tools.monitor.mongodb.core.MongodbRepository;
import org.titan.argus.tools.monitor.mongodb.domain.*;

import java.util.Collection;
import java.util.List;

/**
 * @author starboyate
 */
@Service
public class MongodbServiceImpl implements MongodbService {
	@Override
	public Collection<MongodbNode> getAllMongodbNodeList() {
		return MongodbNodeHolder.getAll();
	}

	@Override
	public MongodbMetricInfo getMongodbMetricInfo(MongodbNode mongodbNode) {
		return MongodbRepository.getMetricInfo(mongodbNode);
	}

	@Override
	public MongodbGlobalLockInfo getMongodbGlobalLockInfo(MongodbNode mongodbNode) {
		return MongodbRepository.getGlobalLock(mongodbNode);
	}

	@Override
	public MongodbLogicalSessionRecordCacheInfo getMongodbSessionInfo(MongodbNode mongodbNode) {
		return MongodbRepository.getSession(mongodbNode);
	}

	@Override
	public MongodbReplInfo getMongodbReplInfo(MongodbNode mongodbNode) {
		return MongodbRepository.getRepl(mongodbNode);
	}

	@Override
	public MongodbOSInfo getMongodbOSInfo(MongodbNode mongodbNode) {
		return MongodbRepository.getOSInfo(mongodbNode);
	}

	@Override
	public MongodbStorageEngineInfo getMongodbStorageEngineInfo(MongodbNode mongodbNode) {
		return MongodbRepository.getStorageEngine(mongodbNode);
	}

	@Override
	public List<MongodbDataBase> getAllDataBaseInfo(MongodbNode node) {
		return MongodbRepository.getAllDataBaseInfo(node);
	}

	@Override
	public String getCollectionSystemInfo(MongodbNode node, String dataBase, String collectionName) {
		return MongodbRepository.getCollectionSystemInfo(node, dataBase, collectionName);
	}
}
