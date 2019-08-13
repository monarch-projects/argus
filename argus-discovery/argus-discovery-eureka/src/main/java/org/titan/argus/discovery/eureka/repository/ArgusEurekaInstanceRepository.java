package org.titan.argus.discovery.eureka.repository;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.titan.argus.discovery.common.disruptor.event.ArgusInstanceOfflineEvent;
import org.titan.argus.discovery.common.disruptor.event.ArgusInstanceRegisteredEvent;
import org.titan.argus.discovery.common.disruptor.event.ArgusInstanceUnknownEvent;
import org.titan.argus.discovery.common.disruptor.producer.InstanceEventProducer;
import org.titan.argus.discovery.common.enums.DiscoveryEventEnum;
import org.titan.argus.discovery.common.repository.InstanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.titan.argus.discovery.common.entities.ArgusInstance;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;


/**
 * @author starboyate
 *
 */
@Component
public class ArgusEurekaInstanceRepository extends InstanceRepository {
	private static final Logger logger = LoggerFactory.getLogger(ArgusEurekaInstanceRepository.class);
	@Autowired
	private EurekaClient eurekaClient;

	private ConcurrentHashMap<String, List<ArgusInstance>> allInstances;

	private ConcurrentHashMap<String, Map<Long, String>> eventMap;

	private final long INTELVAL_NOTIFY_TIME = 60 * 1000;

	@Autowired
	private InstanceEventProducer producer;


	public void init() {
		List<Application> applications = eurekaClient.getApplications().getRegisteredApplications();
		this.allInstances = new ConcurrentHashMap<>(applications.size());
		this.eventMap = new ConcurrentHashMap<>(applications.size());
		loadCache(applications);
	}

	private void loadCache(List<Application> applications) {
		applications.forEach(application -> {
			List<ArgusInstance> argusEurekaInstances = new ArrayList<>(application.size());
			application.getInstancesAsIsFromEureka().forEach(item -> {
				setEvent(item);
				Map<Long, String> temp = this.eventMap.get(item.getId());
				argusEurekaInstances.add(ArgusInstance.builder().id(item.getId()).appName(item.getAppName()).host(item.getHostName()).port(item.getPort()).status(item.getStatus().name())
						.eventMap(temp).build());
			});
			this.allInstances.put(application.getName(), argusEurekaInstances);
		});
	}

	public void update() {
		List<Application> applications = eurekaClient.getApplications().getRegisteredApplications();
		loadCache(applications);
	}



	private void setEvent(InstanceInfo info) {
		Map<Long, String> map = new HashMap<>();
		long currentIntervalTime = System.currentTimeMillis() - info.getLastUpdatedTimestamp();
		Map<Long, String> eventTempMap = null;
		if ((eventTempMap = this.eventMap.get(info.getId())) == null || eventTempMap.get(info.getLastUpdatedTimestamp()) == null) {
			String eventName = null;
			switch (info.getStatus().name().toLowerCase()) {
				case "up":
					eventName = DiscoveryEventEnum.REGISTER.getName();
					if (currentIntervalTime <= INTELVAL_NOTIFY_TIME) {
						producer.onData(new ArgusInstanceRegisteredEvent(info.getId(), info.getAppName(), DiscoveryEventEnum.REGISTER.getName(), info.getLastUpdatedTimestamp()));
					}
					break;
				case "down":
					eventName = DiscoveryEventEnum.OFFLINE.getName();
					if (currentIntervalTime <= INTELVAL_NOTIFY_TIME) {
						producer.onData(new ArgusInstanceOfflineEvent(info.getId(), info.getAppName(), DiscoveryEventEnum.OFFLINE.getName(), info.getLastUpdatedTimestamp()));
					}
					break;
				default:
					eventName = DiscoveryEventEnum.UNKNOWN.getName();
					if (currentIntervalTime <= INTELVAL_NOTIFY_TIME) {
						producer.onData(new ArgusInstanceUnknownEvent(info.getId(), info.getAppName(), DiscoveryEventEnum.UNKNOWN.getName(), info.getLastUpdatedTimestamp()));
					}
					break;
			}
			map.put(info.getLastUpdatedTimestamp(), eventName);
			if (this.eventMap.get(info.getId()) != null) {
				Map<Long, String> tempMap = this.eventMap.get(info.getId());
				tempMap.putAll(map);
			} else {
				this.eventMap.put(info.getId(), map);
			}
		}
	}


	@Override
	public Map<String, List<ArgusInstance>> findAll() {
		if (allInstances == null || allInstances.isEmpty()) {
			init();
		}
		return allInstances;
	}


	/**
	 * get instance by app name
	 * @param appName
	 * @return List<ArgusEurekaInstance>
	 * @throws ExecutionException
	 */
	public List<ArgusInstance> getInstanceByAppName(String appName) {
		if (this.allInstances == null || this.allInstances.isEmpty()) {
			init();
		}
		return this.allInstances.get(appName);
	}

	/**
	 * remove instance
	 * @param appName
	 * @param id
	 */
	public void removeInstance(String appName, String id) {
		Application application = eurekaClient.getApplication(appName);
		InstanceInfo instanceInfo = application.getByInstanceId(id);
		application.removeInstance(instanceInfo);
	}


}
