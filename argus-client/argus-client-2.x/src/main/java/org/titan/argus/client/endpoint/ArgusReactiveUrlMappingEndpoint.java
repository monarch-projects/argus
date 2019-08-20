package org.titan.argus.client.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.condition.PatternsRequestCondition;
import org.springframework.web.reactive.result.condition.RequestMethodsRequestCondition;
import org.springframework.web.reactive.result.method.RequestMappingInfo;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;
import org.titan.argus.plugin.fallback.common.core.ArgusUrlMappingsRepository;
import org.titan.argus.plugin.fallback.hystrix.annotation.HystrixFallback;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author starboyate
 */
@Endpoint(id = "urlMappings")
public class ArgusReactiveUrlMappingEndpoint {

	@Autowired
	private ArgusUrlMappingsRepository argusUrlMappingsRepository;

	@HystrixFallback
	@ReadOperation
	public Map getAllMappings() {
		return this.argusUrlMappingsRepository.getAllUrlMappings();
	}
}
