package org.titan.argus.server.initializer;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.titan.argus.discovery.common.entities.ArgusInstance;
import org.titan.argus.model.entities.InstanceMetadata;
import org.titan.argus.model.entities.RedisNodeInfo;
import org.titan.argus.network.httpclient.util.ArgusHttpClient;
import org.titan.argus.server.core.ArgusActuatorConstant;
import org.titan.argus.server.core.InstanceMetadataHolder;
import org.titan.argus.server.core.MiddleWareNodeHolder;
import org.titan.argus.service.InstanceService;
import org.titan.argus.service.exception.BusinessException;

import java.util.Set;

/**
 * @author starboyate
 */
@Component
public class RedisNodeInitializer extends AbstractArgusNodeInitializer{


	private static final Logger logger = LoggerFactory.getLogger(RedisNodeInitializer.class);

	public RedisNodeInitializer(InstanceMetadataHolder instanceMetadataHolder) {
		super(instanceMetadataHolder);
	}


	@Override
	void initNode(Set<InstanceMetadata> instances) {
		MiddleWareNodeHolder.clearRedisNode();
		instances.stream().filter(InstanceMetadata::getIsUsedRedis).forEach(item -> {
			String url  = item.getIp() + ArgusActuatorConstant.REDIS_NODE;
			try {
				String doGet = this.instanceMetadataHolder.httpClient.doGet(url);
				RedisNodeInfo info = JSONObject.parseObject(doGet, RedisNodeInfo.class);
				info.setId(item.getId());
				MiddleWareNodeHolder.addRedisNodeInfo(info);
			} catch (Exception e) {
				throw new BusinessException(e.getMessage());
			}
		});
	}
}
