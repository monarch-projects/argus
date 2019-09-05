package org.titan.argus.storage.es.service;

import org.titan.argus.storage.es.domain.RedisMonitorNodeInfo;

import java.util.Collection;
import java.util.List;


/**
 * @author starboyate
 */
public interface RedisMonitorNodeInfoService {
	RedisMonitorNodeInfo save(RedisMonitorNodeInfo redisMonitor);

	long count();

	boolean delete(RedisMonitorNodeInfo redisMonitor);

	List<RedisMonitorNodeInfo> findAll();

	List<RedisMonitorNodeInfo> findByIp(String ip);

	List<RedisMonitorNodeInfo> findByTime(Long startTime, Long endTime);

	List<RedisMonitorNodeInfo> findByTimeAndIp(String ip, Long startTime, Long endTime);

	List<RedisMonitorNodeInfo> saveAll(List<RedisMonitorNodeInfo> list);

}
