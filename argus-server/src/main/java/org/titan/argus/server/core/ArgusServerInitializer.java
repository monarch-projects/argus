package org.titan.argus.server.core;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.titan.argus.discovery.common.entities.ArgusInstance;
import org.titan.argus.model.entities.InstanceMetadata;
import org.titan.argus.model.entities.MongodbNodeInfo;
import org.titan.argus.model.entities.RedisNodeInfo;
import org.titan.argus.network.httpclient.util.ArgusHttpClient;
import org.titan.argus.service.InstanceService;
import org.titan.argus.tools.alarm.core.MiddleWareNodeHolder;

import java.util.HashSet;
import java.util.Set;

/**
 * @author starboyate
 */
public class ArgusServerInitializer {
	private Logger logger = LoggerFactory.getLogger(ArgusServerInitializer.class);

	private final InstanceService instanceService;

	private final ArgusHttpClient httpClient;

	public ArgusServerInitializer(InstanceService service, ArgusHttpClient httpClient) {
		this.instanceService = service;
		this.httpClient = httpClient;
	}
	public void init() {
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
	}

	private void initRedisNodeInfo(Set<ArgusInstance> set) {
		set.forEach(item -> {
			String url = item.getHomePageUrl() + ArgusActuatorConstant.REDIS_NODE;
			try {
				String entity = httpClient.doGet(url);
				RedisNodeInfo info = JSONObject.parseObject(entity, RedisNodeInfo.class);
				ArgusMiddleWareNodeHolder.addRedisNodeInfo(info);
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
				ArgusMiddleWareNodeHolder.addMongodbNodeInfo(info);
			} catch (Exception ex) {
				logger.error("http client get fail: {}", ex.getMessage());
			}

		});
	}
}
