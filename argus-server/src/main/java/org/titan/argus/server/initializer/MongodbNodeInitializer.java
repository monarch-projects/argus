package org.titan.argus.server.initializer;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.titan.argus.model.entities.InstanceMetadata;
import org.titan.argus.server.core.ArgusActuatorConstant;
import org.titan.argus.server.core.InstanceMetadataHolder;
import org.titan.argus.service.exception.BusinessException;
import org.titan.argus.tools.monitor.mongodb.core.MongodbNodeHolder;
import org.titan.argus.tools.monitor.mongodb.domain.MongodbNode;
import org.titan.argus.util.SnowFakeIdUtil;

import java.util.Set;

/**
 * @author starboyate
 */
@Component
public class MongodbNodeInitializer extends AbstractArgusNodeInitializer {
	private static final Logger logger = LoggerFactory.getLogger(MongodbNodeInitializer.class);

	public MongodbNodeInitializer(InstanceMetadataHolder instanceMetadataHolder) {
		super(instanceMetadataHolder);
	}


	@Override
	void initNode(Set<InstanceMetadata> instances) {
		instances.stream().filter(InstanceMetadata::getIsUsedMongodb).forEach(item -> {
			String url  = item.getIp() + ArgusActuatorConstant.MONGODB_NODE;
			try {
				String doGet = this.instanceMetadataHolder.httpClient.doGet(url);
				MongodbNode info = JSONObject.parseObject(doGet, MongodbNode.class);
				info.setId(SnowFakeIdUtil.snowFakeId());
				MongodbNodeHolder.add(info);
			} catch (Exception e) {
				throw new BusinessException(e.getMessage());
			}
		});
	}


}
