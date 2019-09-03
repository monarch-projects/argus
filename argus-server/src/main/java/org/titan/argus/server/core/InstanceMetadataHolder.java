package org.titan.argus.server.core;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.titan.argus.discovery.common.entities.ArgusInstance;
import org.titan.argus.model.entities.InstanceMetadata;
import org.titan.argus.network.httpclient.util.ArgusHttpClient;
import org.titan.argus.service.InstanceService;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author starboyate
 */
@Component
public class InstanceMetadataHolder {

	@Autowired
	public InstanceService instanceService;

	@Autowired
	public ArgusHttpClient httpClient;

	private Set<InstanceMetadata> allInstanceMetadata = Sets.newConcurrentHashSet();

	private static final Logger logger = LoggerFactory.getLogger(InstanceMetadataHolder.class);

	public Set<InstanceMetadata> getAllInstanceMetadata() {
		Set<ArgusInstance> allInstanceSet = instanceService.findAll();
		ArgusInstanceMetaOptional optional = getDifferenceSet(
				allInstanceSet.stream()
						.map(item -> InstanceMetadata.builder().id(item.getId()).appName(item.getAppName())
								.ip(item.getHomePageUrl()).status(item.getStatus()).eventMap(item.getEventMap())
								.port(item.getPort()).build())
						.collect(Collectors.toSet()), this.allInstanceMetadata);
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
			String url = instance.getIp() + ArgusActuatorConstant.META_INFO;
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
		Validate.notNull(source, "source elements must not be null");
		Validate.notNull(target, "source elements must not be null");
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
