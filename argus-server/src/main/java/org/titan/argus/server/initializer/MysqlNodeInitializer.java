package org.titan.argus.server.initializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.titan.argus.discovery.common.entities.ArgusInstance;
import org.titan.argus.network.httpclient.util.ArgusHttpClient;
import org.titan.argus.server.core.InstanceMetadataHolder;
import org.titan.argus.service.InstanceService;

import java.util.Set;

/**
 * @author starboyate
 */
@Component
public class MysqlNodeInitializer extends AbstractArgusNodeInitializer {
	private static final Logger logger = LoggerFactory.getLogger(MysqlNodeInitializer.class);

	public MysqlNodeInitializer(InstanceMetadataHolder instanceMetadataHolder) {
		super(instanceMetadataHolder);
	}


	@Override
	void initNode(Set set) {
		logger.info("init");
	}

}
