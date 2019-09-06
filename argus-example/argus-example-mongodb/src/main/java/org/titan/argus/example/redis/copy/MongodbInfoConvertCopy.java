//package org.titan.argus.example.redis.core;
//
//import org.apache.commons.lang3.Validate;
//import org.titan.argus.example.redis.domain.*;
//
//
///**
// * @author starboyate
// */
//public class MongodbInfoConvertCopy {
//	public MongodbMonitorNodeInfo convertToMongodbMonitorNodeInfo(MongodbClientConnectionsInfo mongodbClientConnectionsInfo,
//			MongodbGlobalLockInfo mongodbGlobalLockInfo,
//			MongodbLogicalSessionRecordCacheInfo mongodbLogicalSessionRecordCacheInfo,
//			MongodbNetworkInfo mongodbNetworkInfo,
//			MongodbOpcountersInfo mongodbOpcountersInfo,
//			MongodbStorageEngineInfo mongodbStorageEngineInfo,
//			MongodbMemInfo mongodbMemInfo,
//			MongodbReplInfo mongodbReplInfo) {
//		Validate.notNull(mongodbClientConnectionsInfo);
//		Validate.notNull(mongodbGlobalLockInfo);
//		Validate.notNull(mongodbLogicalSessionRecordCacheInfo);
//		Validate.notNull(mongodbNetworkInfo);
//		Validate.notNull(mongodbOpcountersInfo);
//		Validate.notNull(mongodbStorageEngineInfo);
//		Validate.notNull(mongodbMemInfo);
//		Validate.notNull(mongodbReplInfo);
//		return MongodbMonitorNodeInfo.builder()
//				.clientConnectionsCurrent(mongodbClientConnectionsInfo.getCurrent())
//				.clientConnectionsActive(mongodbClientConnectionsInfo.getActive())
//				.clientConnectionsActiveClientsReaders(mongodbClientConnectionsInfo.getActiveClientsReaders())
//				.clientConnectionsActiveClientsWriters(mongodbClientConnectionsInfo.getActiveClientsWriters())
//				.clientConnectionsAvailable(mongodbClientConnectionsInfo.getAvailable())
//				.clientConnectionsTotalCreated(mongodbClientConnectionsInfo.getTotalCreated())
//				.globalLockReaders(mongodbGlobalLockInfo.getMongodbGlobalLockActiveClientsInfo().getReaders())
//				.globalLockTotal(mongodbGlobalLockInfo.getMongodbGlobalLockActiveClientsInfo().getTotal())
//				.globalLockTotalTime(mongodbGlobalLockInfo.getTotalTime())
//				.globalLockWriters(mongodbGlobalLockInfo.getMongodbGlobalLockActiveClientsInfo().getWriters())
//				.logicalSessionRecordCacheActiveSessionsCount(mongodbLogicalSessionRecordCacheInfo.getActiveSessionsCount())
//				.logicalSessionRecordCacheLastSessionsCollectionJobCursorsClosed(mongodbLogicalSessionRecordCacheInfo.getLastSessionsCollectionJobCursorsClosed())
//				.logicalSessionRecordCacheLastSessionsCollectionJobDurationMillis(mongodbLogicalSessionRecordCacheInfo.getLastSessionsCollectionJobDurationMillis())
//				.logicalSessionRecordCacheLastSessionsCollectionJobEntriesEnded(mongodbLogicalSessionRecordCacheInfo.getLastSessionsCollectionJobEntriesEnded())
//				.logicalSessionRecordCacheLastSessionsCollectionJobEntriesRefreshed(mongodbLogicalSessionRecordCacheInfo.getLastSessionsCollectionJobEntriesRefreshed())
//				.logicalSessionRecordCacheLastSessionsCollectionJobTimestamp(mongodbLogicalSessionRecordCacheInfo.getLastSessionsCollectionJobTimestamp())
//				.logicalSessionRecordCacheLastTransactionReaperJobDurationMillis(mongodbLogicalSessionRecordCacheInfo.getLastTransactionReaperJobDurationMillis())
//				.logicalSessionRecordCacheLastTransactionReaperJobEntriesCleanedUp(mongodbLogicalSessionRecordCacheInfo.getLastTransactionReaperJobEntriesCleanedUp())
//				.logicalSessionRecordCacheLastTransactionReaperJobTimestamp(mongodbLogicalSessionRecordCacheInfo.getLastTransactionReaperJobTimestamp())
//				.logicalSessionRecordCacheSessionsCollectionJobCount(mongodbLogicalSessionRecordCacheInfo.getSessionsCollectionJobCount())
//				.logicalSessionRecordCacheTransactionReaperJobCount(mongodbLogicalSessionRecordCacheInfo.getTransactionReaperJobCount())
//				.networkBytesIn(mongodbNetworkInfo.getBytesIn())
//				.networkBytesOut(mongodbNetworkInfo.getBytesOut())
//				.networkNumRequests(mongodbNetworkInfo.getNumRequests())
//				.opcountersCommand(mongodbOpcountersInfo.getCommand())
//				.opcountersDelete(mongodbOpcountersInfo.getDelete())
//				.opcountersGetMore(mongodbOpcountersInfo.getGetMore())
//				.opcountersInsert(mongodbOpcountersInfo.getInsert())
//				.opcountersQuery(mongodbOpcountersInfo.getQuery())
//				.opcountersUpdate(mongodbOpcountersInfo.getUpdate())
//				.storageEngineName(mongodbStorageEngineInfo.getName())
//				.storageEnginePersistent(mongodbStorageEngineInfo.getPersistent())
//				.storageEngineSupportsCommittedReads(mongodbStorageEngineInfo.getSupportsCommittedReads())
//				.replApplyBatchSize(mongodbReplInfo.getMongodbRelicationApplyInfo().getBatchSize())
//				.replApplyBatchesNum(mongodbReplInfo.getMongodbRelicationApplyInfo().getMongodbReplicationApplyBatchesInfo().getNum())
//				.replApplyBatchesTotalMillis(mongodbReplInfo.getMongodbRelicationApplyInfo().getMongodbReplicationApplyBatchesInfo().getTotalMillis())
//				.replBufferCount(mongodbReplInfo.getMongodbRelicationBufferInfo().getCount())
//				.replBufferMaxSizeBytes(mongodbReplInfo.getMongodbRelicationBufferInfo().getMaxSizeBytes())
//				.replBufferSizeBytes(mongodbReplInfo.getMongodbRelicationBufferInfo().getSizeBytes())
//				.replNetworkBytes(mongodbReplInfo.getMongodbReplicationNetworkInfo().getBytes())
//				.replNetworkGetMoresNum(mongodbReplInfo.getMongodbReplicationNetworkInfo().getMongodbReplicationNetworkGetMoreInfo().getNum())
//				.replNetworkGetMoresTotalMillis(mongodbReplInfo.getMongodbReplicationNetworkInfo().getMongodbReplicationNetworkGetMoreInfo().getTotalMillis())
//				.replNetworkOps(mongodbReplInfo.getMongodbReplicationNetworkInfo().getOps())
//				.replNetworkReadersCreated(mongodbReplInfo.getMongodbReplicationNetworkInfo().getReadersCreated())
//				.memResident(mongodbMemInfo.getResident())
//				.memVirtual(mongodbMemInfo.getVirtual())
//				.build();
//
//	}
//
//	public static MongodbMemInfo convertToMemInfo(MongodbMonitorNodeInfo mongodbMonitorNodeInfo) {
//		return MongodbMemInfo.builder()
//				.resident(mongodbMonitorNodeInfo.getMemResident())
//				.virtual(mongodbMonitorNodeInfo.getMemVirtual())
//				.build();
//	}
//
//	public static MongodbClientConnectionsInfo convertToClientConnection(MongodbMonitorNodeInfo mongodbMonitorNodeInfo) {
//		return MongodbClientConnectionsInfo.builder()
//				.active(mongodbMonitorNodeInfo.getClientConnectionsActive())
//				.activeClientsReaders(mongodbMonitorNodeInfo.getClientConnectionsActiveClientsReaders())
//				.activeClientsWriters(mongodbMonitorNodeInfo.getClientConnectionsActiveClientsWriters())
//				.build();
//	}
//
//	public static MongodbOpcountersInfo convertToOpcounters(MongodbMonitorNodeInfo mongodbMonitorNodeInfo) {
//		return MongodbOpcountersInfo.builder()
//				.command(mongodbMonitorNodeInfo.getOpcountersCommand())
//				.delete(mongodbMonitorNodeInfo.getOpcountersDelete())
//				.getMore(mongodbMonitorNodeInfo.getOpcountersGetMore())
//				.insert(mongodbMonitorNodeInfo.getOpcountersInsert())
//				.query(mongodbMonitorNodeInfo.getOpcountersQuery())
//				.update(mongodbMonitorNodeInfo.getOpcountersUpdate())
//				.build();
//	}
//
//	public static MongodbNetworkInfo convertToNetwork(MongodbMonitorNodeInfo mongodbMonitorNodeInfo) {
//		return MongodbNetworkInfo.builder()
//				.numRequests(mongodbMonitorNodeInfo.getNetworkNumRequests())
//				.bytesOut(mongodbMonitorNodeInfo.getNetworkBytesOut())
//				.bytesIn(mongodbMonitorNodeInfo.getNetworkBytesIn())
//				.build();
//	}
//}
