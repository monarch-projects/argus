package org.titan.argus.server.initializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.titan.argus.discovery.common.entities.ArgusInstance;
import org.titan.argus.model.entities.InstanceMetadata;
import org.titan.argus.network.httpclient.util.ArgusHttpClient;
import org.titan.argus.server.core.InstanceMetadataHolder;
import org.titan.argus.service.InstanceService;

import java.util.Set;

/**
 * @author starboyate
 */
public abstract class AbstractArgusNodeInitializer implements ArgusInitializer {
	private static final Logger logger = LoggerFactory.getLogger(AbstractArgusNodeInitializer.class);


	final InstanceMetadataHolder instanceMetadataHolder;


	public AbstractArgusNodeInitializer(InstanceMetadataHolder instanceMetadataHolder) {
		this.instanceMetadataHolder = instanceMetadataHolder;
	}

	abstract void initNode(Set<InstanceMetadata> instances);

	@Override
	public void init() {
		Set<InstanceMetadata> allInstanceMetadata = this.instanceMetadataHolder.getAllInstanceMetadata();
		initNode(allInstanceMetadata);
	}
}
