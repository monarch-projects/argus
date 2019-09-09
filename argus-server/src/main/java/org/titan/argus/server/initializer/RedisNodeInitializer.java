package org.titan.argus.server.initializer;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.titan.argus.model.entities.InstanceMetadata;
import org.titan.argus.server.core.ArgusActuatorConstant;
import org.titan.argus.server.core.InstanceMetadataHolder;
import org.titan.argus.service.exception.BusinessException;
import org.titan.argus.tools.monitor.redis.core.RedisNodeHolder;
import org.titan.argus.tools.monitor.redis.domain.RedisNode;
import org.titan.argus.util.SnowFakeIdUtil;

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
	void initNode(InstanceMetadata instanceMetadata) {
		if (instanceMetadata.getIsUsedRedis()) {
			String url  = instanceMetadata.getIp() + ArgusActuatorConstant.REDIS_NODE;
			try {
				String doGet = this.instanceMetadataHolder.httpClient.doGet(url);
				RedisNode info = JSONObject.parseObject(doGet, RedisNode.class);
				info.setId(SnowFakeIdUtil.snowFakeId());
				RedisNodeHolder.add(info);
			} catch (Exception e) {
				throw new BusinessException(e.getMessage());
			}
		}
	}
}
