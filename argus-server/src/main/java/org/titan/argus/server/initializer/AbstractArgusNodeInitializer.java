package org.titan.argus.server.initializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.titan.argus.discovery.common.entities.ArgusInstance;
import org.titan.argus.network.httpclient.util.ArgusHttpClient;
import org.titan.argus.service.InstanceService;

import java.util.Set;

/**
 * @author starboyate
 */
public abstract class AbstractArgusNodeInitializer<T> implements ArgusInitializer {
	private static final Logger logger = LoggerFactory.getLogger(AbstractArgusNodeInitializer.class);

	 final InstanceService instanceService;

	 final ArgusHttpClient httpClient;

	public AbstractArgusNodeInitializer(InstanceService instanceService, ArgusHttpClient httpClient) {
		this.instanceService = instanceService;
		this.httpClient = httpClient;
	}

	abstract void initNode(Set<ArgusInstance> instances);

	@Override
	public void init() {
		Set<ArgusInstance> instances = this.instanceService.findAll();
		initNode(instances);
	}
}
