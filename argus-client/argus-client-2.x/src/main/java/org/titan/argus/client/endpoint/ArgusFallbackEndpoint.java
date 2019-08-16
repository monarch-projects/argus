package org.titan.argus.client.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.titan.argus.plugin.fallback.common.core.ArgusUrlMappingsRepository;
import org.titan.argus.plugin.fallback.common.entities.ArgusUrlMapping;

/**
 * @author starboyate
 */
@RestControllerEndpoint(id = "fallback")
public class ArgusFallbackEndpoint {
	@Autowired
	private ArgusUrlMappingsRepository argusUrlMappingsRepository;

	@PostMapping
	public Object updateFallbackRoute(@RequestBody ArgusUrlMapping argusUrlMapping) {
		return this.argusUrlMappingsRepository.setFallbackProperties(argusUrlMapping.getFallbackProperties(), argusUrlMapping.getMethodName());
	}

}
