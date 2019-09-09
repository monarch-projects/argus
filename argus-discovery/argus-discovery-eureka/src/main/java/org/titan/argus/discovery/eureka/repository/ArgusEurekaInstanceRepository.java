package org.titan.argus.discovery.eureka.repository;

import com.google.common.collect.Sets;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.titan.argus.discovery.common.disruptor.event.ArgusInstanceOfflineEvent;
import org.titan.argus.discovery.common.disruptor.event.ArgusInstanceRegisteredEvent;
import org.titan.argus.discovery.common.disruptor.event.ArgusInstanceUnknownEvent;
import org.titan.argus.discovery.common.disruptor.producer.InstanceEventProducer;
import org.titan.argus.discovery.common.enums.DiscoveryEventEnum;
import org.titan.argus.discovery.common.event.InstanceUpdateEvent;
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

	@Autowired
	private ApplicationEventPublisher publisher;

	private ConcurrentHashMap<String, List<ArgusInstance>> ALL_INSTANCES;

	private ConcurrentHashMap<String, Map<String, String>> EVENT_MAP;

	private Set<ArgusInstance> ALL_INSTANCE_SET;

	private final long INTELVAL_NOTIFY_TIME = 60 * 1000;

	private final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

	@Autowired
	private InstanceEventProducer producer;


	public void init() {
		List<Application> applications = eurekaClient.getApplications().getRegisteredApplications();
		this.ALL_INSTANCES = new ConcurrentHashMap<>(applications.size());
		this.EVENT_MAP = new ConcurrentHashMap<>(applications.size());
		this.ALL_INSTANCE_SET = Sets.newConcurrentHashSet();
		loadCache(applications);
	}

	private void loadCache(List<Application> applications) {
		applications.forEach(application -> {
			List<ArgusInstance> argusEurekaInstances = new ArrayList<>(application.size());
			application.getInstancesAsIsFromEureka().forEach(item -> {
				setEvent(item);
				Map<String, String> temp = this.EVENT_MAP.get(item.getId());
				ArgusInstance build = ArgusInstance.builder().id(item.getId()).appName(item.getAppName())
						.host(item.getIPAddr()).homePageUrl(item.getHomePageUrl()).port(item.getPort()).status(item.getStatus().name()).eventMap(temp)
						.build();
				argusEurekaInstances.add(build);
				boolean add = this.ALL_INSTANCE_SET.add(build);
				if (add) {
					this.publisher.publishEvent(new InstanceUpdateEvent(this, build));
				}
			});
			this.ALL_INSTANCES.put(application.getName(), argusEurekaInstances);
		});
	}


	public void update() {
		List<Application> applications = eurekaClient.getApplications().getRegisteredApplications();
		loadCache(applications);
	}



	private void setEvent(InstanceInfo info) {
		Map<String, String> map = new HashMap<>();
		long currentIntervalTime = System.currentTimeMillis() - info.getLastUpdatedTimestamp();
		Map<String, String> eventTempMap;
		String time = DateFormatUtils.format(new Date(info.getLastUpdatedTimestamp()), DATE_PATTERN);
		if ((eventTempMap = this.EVENT_MAP.get(info.getId())) == null || eventTempMap.get(time) == null) {
			String eventName;
			switch (info.getStatus().name().toLowerCase()) {
				case "up":
					eventName = DiscoveryEventEnum.REGISTER.getName();
					if (currentIntervalTime <= INTELVAL_NOTIFY_TIME) {
						producer.publish(new ArgusInstanceRegisteredEvent(this, info.getId(), info.getAppName(), DiscoveryEventEnum.REGISTER.getName(), time));
						this.publisher.publishEvent(new ArgusInstanceRegisteredEvent(this, info.getId(), info.getAppName(), DiscoveryEventEnum.REGISTER.getName(), time));
					}
					break;
				case "down":
					eventName = DiscoveryEventEnum.OFFLINE.getName();
					if (currentIntervalTime <= INTELVAL_NOTIFY_TIME) {
						producer.publish(new ArgusInstanceOfflineEvent(this, info.getId(), info.getAppName(), DiscoveryEventEnum.OFFLINE.getName(), time));
						this.publisher.publishEvent(new ArgusInstanceOfflineEvent(this, info.getId(), info.getAppName(), DiscoveryEventEnum.REGISTER.getName(), time));
					}
					break;
				default:
					eventName = DiscoveryEventEnum.UNKNOWN.getName();
					if (currentIntervalTime <= INTELVAL_NOTIFY_TIME) {
						producer.publish(new ArgusInstanceUnknownEvent(this, info.getId(), info.getAppName(), DiscoveryEventEnum.UNKNOWN.getName(), time));
						this.publisher.publishEvent(new ArgusInstanceUnknownEvent(this, info.getId(), info.getAppName(), DiscoveryEventEnum.REGISTER.getName(), time));
					}
					break;
			}
			map.put(time, eventName);
			Map<String, String> tempMap;
			if ((tempMap = this.EVENT_MAP.get(info.getId())) != null) {
				tempMap.putAll(map);
			} else {
				this.EVENT_MAP.put(info.getId(), map);
			}
		}
	}




	/**
	 * get instance by app name
	 * @param appName
	 * @return List<ArgusEurekaInstance>
	 * @throws ExecutionException
	 */
	public List<ArgusInstance> getInstanceByAppName(String appName) {
		if (this.ALL_INSTANCES == null || this.ALL_INSTANCES.isEmpty()) {
			init();
		}
		return this.ALL_INSTANCES.get(appName);
	}

	@Override
	public ArgusInstance getInstanceById(String id) {
		return this.ALL_INSTANCE_SET.stream().filter(instance -> instance.getId().equals(id)).findFirst().orElse(null);
	}

	@Override
	public Set<ArgusInstance>  findAll() {
		return this.ALL_INSTANCE_SET;
	}

	@Override
	public List<ArgusInstance> page(int fromIndex, int toIndex) {
		return new ArrayList<>(this.ALL_INSTANCE_SET).subList(fromIndex, toIndex);
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
