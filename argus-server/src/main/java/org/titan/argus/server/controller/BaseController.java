package org.titan.argus.server.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.titan.argus.discovery.common.entities.ArgusInstance;
import org.titan.argus.model.entities.InstanceMetadata;
import org.titan.argus.model.response.BaseResponse;
import org.titan.argus.network.httpclient.util.ArgusHttpClient;
import org.titan.argus.server.core.ArgusActuatorConstant;
import org.titan.argus.service.InstanceService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author starboyate
 */
@RestController
public abstract class BaseController {

	private Logger logger = LoggerFactory.getLogger(BaseController.class);

	@Autowired
	InstanceService instanceService;

	@Autowired
	ArgusHttpClient httpClient;

	private Set<InstanceMetadata> allInstanceMetadata = Sets.newConcurrentHashSet();



	BaseResponse proxyGet(String suffix, String id) {
		ArgusInstance instance = this.instanceService.getInstanceById(id);
		String url = getUrl(instance.getHomePageUrl(), suffix);
		String entity;
		try {
			entity = httpClient.doGet(url);
		} catch (Exception ex) {
			return BaseResponse.error(null);
		}

		return BaseResponse.success(JSON.parseObject(entity, Object.class));
	}

	 BaseResponse proxyPost(String suffix, String id, Object requestBody) {
		ArgusInstance instance = this.instanceService.getInstanceById(id);
		String url = getUrl(instance.getHomePageUrl(), suffix);
		ArgusHttpClient.HttpResponse httpResponse;
		try {
			httpResponse = httpClient.doPost(url, requestBody);
		} catch (Exception ex) {
			return BaseResponse.error(null);
		}
		return BaseResponse.success(JSON.parseObject(httpResponse.getBody(), Object.class));
	}

	String getUrl(String prefix, String suffix) {
		return prefix + suffix;
	}

	Set<InstanceMetadata> getAllInstanceMetadata() {
		Set<ArgusInstance> allInstanceSet = instanceService.findAll();
		ArgusInstanceMetaOptional optional = getDifferenceSet(allInstanceSet.stream()
				.map(item -> InstanceMetadata.builder().id(item.getId()).appName(item.getAppName())
						.ip(item.getHomePageUrl()).status(item.getStatus()).eventMap(item.getEventMap())
						.port(item.getPort()).build()).collect(Collectors.toSet()), this.allInstanceMetadata);
		switch (optional) {
			case ADD:
				addInstanceMeta(optional.getMetadata());
				break;
			case REMOVE:
				removeInstanceMeta(optional.getMetadata());
				break;
			case NOTHING:
			default:
				break;
		}
		return this.allInstanceMetadata;
	}

	private void removeInstanceMeta(Set<InstanceMetadata> set) {
		this.allInstanceMetadata.removeAll(set);
	}

	private void addInstanceMeta(Set<InstanceMetadata> set) {
		set.forEach(instance -> {
			String url = getUrl(instance.getIp(), ArgusActuatorConstant.META_INFO);
			try {
				String entity = httpClient.doGet(url);
				InstanceMetadata metadata = JSON.parseObject(entity, InstanceMetadata.class);
				metadata.setAppName(instance.getAppName());
				metadata.setEventMap(instance.getEventMap());
				metadata.setId(instance.getId());
				metadata.setIp(instance.getIp());
				metadata.setPort(instance.getPort());
				metadata.setStatus(instance.getStatus());
				this.allInstanceMetadata.add(metadata);
			} catch (Exception ex) {
				logger.error("http client get fail: {}", ex.getMessage());
			}
		});
	}

	private ArgusInstanceMetaOptional getDifferenceSet(Set<InstanceMetadata> source, Set<InstanceMetadata> target) {
		Set<InstanceMetadata> result = new HashSet<>(source);
		result.removeAll(target);
		if (result.size() > 0) {
			ArgusInstanceMetaOptional.ADD.setMetadata(result);
			return ArgusInstanceMetaOptional.ADD;
		}
		result.clear();
		result.addAll(target);
		result.removeAll(source);
		if (result.size() > 0) {
			ArgusInstanceMetaOptional.REMOVE.setMetadata(result);
			return ArgusInstanceMetaOptional.REMOVE;
		}
		return ArgusInstanceMetaOptional.NOTHING;
	}


	private enum  ArgusInstanceMetaOptional {
		ADD,REMOVE,NOTHING;
		private Set<InstanceMetadata> metadata;

		public Set<InstanceMetadata> getMetadata() {
			return metadata;
		}

		public void setMetadata(Set<InstanceMetadata> metadata) {
			this.metadata = new HashSet<>(metadata);
		}
	}
}
