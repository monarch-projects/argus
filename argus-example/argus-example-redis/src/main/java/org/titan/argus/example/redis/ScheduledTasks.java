package org.titan.argus.example.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.titan.argus.example.redis.es.RedisMonitorRepository;

import java.util.Set;

/**
 * @author starboyate
 */
@Component
public class ScheduledTasks {
	@Autowired
	private RedisMonitorRepository redisMonitorRepository;
	@Scheduled(fixedRate = 1000 * 60, initialDelay = 1000 * 60 * 2)
	public void monitor() {
		Set<RedisNodeInfo> redisNodeInfos = RedisNodeInfoHolder.get();
		redisNodeInfos.forEach(item -> {
			new Thread(() -> JedisUtil.getRedisMonitor(item)).start();
		});
	}
}
