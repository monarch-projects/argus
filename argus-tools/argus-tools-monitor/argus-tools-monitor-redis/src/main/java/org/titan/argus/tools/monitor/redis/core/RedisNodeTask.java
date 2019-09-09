package org.titan.argus.tools.monitor.redis.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.titan.argus.storage.es.domain.RedisMonitorNodeInfo;
import org.titan.argus.storage.es.service.RedisMonitorNodeInfoService;
import org.titan.argus.tools.monitor.redis.domain.RedisNode;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author starboyate
 */
@Component
public class RedisNodeTask {
	private Logger logger = LoggerFactory.getLogger(RedisNodeTask.class);
	@Autowired
	private RedisMonitorNodeInfoService redisMonitorNodeInfoService;

	private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
	@Scheduled(fixedRate = 1000 * 60, initialDelay = 1000 * 60 * 2)
	public void monitor() {
		Collection<RedisNode> redisNodes = RedisNodeHolder.getAll();
		redisNodes.forEach(item -> executorService.submit(() -> {
			List<RedisMonitorNodeInfo> redisMonitorNodeInfoList = RedisRepository.getRedisMonitor(item);
			this.redisMonitorNodeInfoService.saveAll(redisMonitorNodeInfoList);
			logger.info("save redis metric to es");
		}));
	}
}
