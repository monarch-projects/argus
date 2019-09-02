package org.titan.argus.server.initializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.titan.argus.discovery.common.entities.ArgusInstance;
import org.titan.argus.network.httpclient.util.ArgusHttpClient;
import org.titan.argus.service.InstanceService;

import java.util.Set;

/**
 * @author starboyate
 */
@Component
public class RedisNodeInitializer extends AbstractArgusNodeInitializer{

	private static final Logger logger = LoggerFactory.getLogger(RedisNodeInitializer.class);

	public RedisNodeInitializer(InstanceService instanceService, ArgusHttpClient httpClient) {
		super(instanceService, httpClient);
	}

	@Override
	void initNode(Set<ArgusInstance> instances) {
		logger.info("init");
	}

}
