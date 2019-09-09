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

	List<RedisMonitorNodeInfo> findAll(Integer page, Integer size);

	List<RedisMonitorNodeInfo> findByIp(String ip, Integer page, Integer size);

	List<RedisMonitorNodeInfo> findByTime(Long startTime, Long endTime, Integer page, Integer size);

	List<RedisMonitorNodeInfo> findByTimeAndIp(String ip, Long startTime, Long endTime, Integer page, Integer size);

	List<RedisMonitorNodeInfo> saveAll(List<RedisMonitorNodeInfo> list);

}
