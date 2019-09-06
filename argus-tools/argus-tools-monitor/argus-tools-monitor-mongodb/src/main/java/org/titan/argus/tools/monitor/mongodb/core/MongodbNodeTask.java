package org.titan.argus.tools.monitor.mongodb.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.titan.argus.storage.es.domain.MongodbMonitorNodeInfo;
import org.titan.argus.storage.es.service.MongodbMonitorNodeInfoService;
import org.titan.argus.tools.monitor.mongodb.domain.MongodbNode;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author starboyate
 */
@Component
public class MongodbNodeTask {
	@Autowired
	private MongodbMonitorNodeInfoService mongodbMonitorNodeInfoService;

	private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
	@Scheduled(fixedRate = 1000 * 60, initialDelay = 1000 * 60 * 2)
	public void monitor() {
		Set<MongodbNode> mongodbNodes = MongodbNodeHolder.get();
		mongodbNodes.forEach(item -> executorService.submit(() -> {
			MongodbMonitorNodeInfo monitorNodeInfo = MongodbRepository.getMonitorNodeInfo(item);
			this.mongodbMonitorNodeInfoService.save(monitorNodeInfo);
		}));
	}
}
