package org.titan.argus.client.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.titan.argus.plugin.fallback.common.core.ArgusUrlMappingsRepository;
import org.titan.argus.plugin.fallback.common.entities.ArgusUrlMapping;
import org.titan.argus.plugin.fallback.hystrix.annotation.HystrixFallback;

import java.util.*;

/**
 * @author starboyate
 */
@Endpoint(id = "urlMappings")
public class ArgusUrlMappingEndpoint {

	@Autowired
	private ArgusUrlMappingsRepository argusUrlMappingsRepository;

	@HystrixFallback
	@ReadOperation
	public Map<String, ArgusUrlMapping> getAllMappings() {
		return this.argusUrlMappingsRepository.getAllUrlMappings();
	}


}
