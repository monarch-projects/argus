
package org.titan.argus.server.core;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.titan.argus.discovery.common.entities.ArgusInstance;
import org.titan.argus.model.entities.Alarm;
import org.titan.argus.model.entities.InstanceMetadata;
import org.titan.argus.model.entities.MongodbNodeInfo;
import org.titan.argus.model.entities.RedisNodeInfo;
import org.titan.argus.network.httpclient.util.ArgusHttpClient;
import org.titan.argus.service.AlarmService;
import org.titan.argus.service.InstanceService;
import org.titan.argus.tools.alarm.core.AlarmHolder;
import org.titan.argus.tools.alarm.core.MiddleWareNodeHolder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author starboyate
 */
public class ArgusServerInitializer implements ApplicationListener<ContextRefreshedEvent> {
	private Logger logger = LoggerFactory.getLogger(ArgusServerInitializer.class);

	private final InstanceService instanceService;

	private final ArgusHttpClient httpClient;

	private final AlarmService alarmService;

	private final ApplicationEventPublisher publisher;

	public ArgusServerInitializer(InstanceService instanceService, ArgusHttpClient httpClient, AlarmService alarmService, ApplicationEventPublisher publisher) {
		this.instanceService = instanceService;
		this.httpClient = httpClient;
		this.alarmService = alarmService;
		this.publisher = publisher;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		init();
	}

	private void init() {
		Set<ArgusInstance> instances = this.instanceService.findAll();
		Set<ArgusInstance> redisInstanceSet = new HashSet<>();
		Set<ArgusInstance> mongodbInstanceSet = new HashSet<>();
		instances.forEach(instance -> {
			String url = instance.getHomePageUrl() + ArgusActuatorConstant.META_INFO;
			try {
				String entity = httpClient.doGet(url);
				InstanceMetadata metadata = JSON.parseObject(entity, InstanceMetadata.class);
				if (metadata.getIsUsedRedis()) {
					redisInstanceSet.add(instance);
				}
			} catch (Exception ex) {
				logger.error("http client get fail: {}", ex.getMessage());
			}
		});
		initRedisNodeInfo(redisInstanceSet);
		initMongodbNodeInfo(mongodbInstanceSet);
		initAlarm();
	}

	private void initRedisNodeInfo(Set<ArgusInstance> set) {
		set.forEach(item -> {
			String url = item.getHomePageUrl() + ArgusActuatorConstant.REDIS_NODE;
			try {
				String entity = httpClient.doGet(url);
				RedisNodeInfo info = JSONObject.parseObject(entity, RedisNodeInfo.class);
				info.setId(item.getId());
				MiddleWareNodeHolder.addRedisNodeInfo(info);
			} catch (Exception ex) {
				logger.error("http client get fail: {}", ex.getMessage());
			}

		});
	}

	private void initMongodbNodeInfo(Set<ArgusInstance> set) {
		set.forEach(item -> {
			String url = item.getHomePageUrl() + ArgusActuatorConstant.MONGODB_NODE;
			try {
				String entity = httpClient.doGet(url);
				MongodbNodeInfo info = JSONObject.parseObject(entity, MongodbNodeInfo.class);
				MiddleWareNodeHolder.addMongodbNodeInfo(info);
			} catch (Exception ex) {
				logger.error("http client get fail: {}", ex.getMessage());
			}

		});
	}

	private void initAlarm() {
		List<Alarm> list = this.alarmService.list();
		if (!list.isEmpty()) {
			AlarmHolder.addAllAlarm(list);
		}
	}
}
