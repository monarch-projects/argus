package org.titan.argus.storage.es.service;

import org.titan.argus.storage.es.domain.MongodbMonitorNodeInfo;
import org.titan.argus.storage.es.domain.RedisMonitorNodeInfo;

import java.util.List;


/**
 * @author starboyate
 */
public interface MongodbMonitorNodeInfoService {
	MongodbMonitorNodeInfo save(MongodbMonitorNodeInfo mongodbMonitorNodeInfo);

	long count();

	boolean delete(MongodbMonitorNodeInfo mongodbMonitorNodeInfo);

	List<MongodbMonitorNodeInfo> findAll();

	List<MongodbMonitorNodeInfo> findByIp(String ip);

	List<MongodbMonitorNodeInfo> findByTime(Long startTime, Long endTime);

	List<MongodbMonitorNodeInfo> findByTimeAndIp(String ip, Long startTime, Long endTime);

	List<MongodbMonitorNodeInfo> saveAll(List<MongodbMonitorNodeInfo> list);

}
