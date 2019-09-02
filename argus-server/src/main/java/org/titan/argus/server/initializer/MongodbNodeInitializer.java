package org.titan.argus.server.initializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.titan.argus.network.httpclient.util.ArgusHttpClient;
import org.titan.argus.service.InstanceService;

import java.util.Set;

/**
 * @author starboyate
 */
@Component
public class MongodbNodeInitializer extends AbstractArgusNodeInitializer {
	private static final Logger logger = LoggerFactory.getLogger(MongodbNodeInitializer.class);

	public MongodbNodeInitializer(InstanceService service, ArgusHttpClient client) {
		super(service, client);
	}

	@Override
	void initNode(Set set) {
		logger.info("init");
	}


}
