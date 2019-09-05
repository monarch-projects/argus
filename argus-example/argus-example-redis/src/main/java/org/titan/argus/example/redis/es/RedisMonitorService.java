package org.titan.argus.example.redis.es;

import java.util.List;

/**
 * @author starboyate
 */
public interface RedisMonitorService {
	RedisNodeInfo save(RedisNodeInfo redisMonitor);

	long count();

	boolean delete(RedisNodeInfo redisMonitor);

	List<RedisNodeInfo> findAll();

	List<RedisNodeInfo> findByIp(String ip);

}
