package org.titan.argus.plugin.fallback.hystrix.repository.reactive;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.condition.PatternsRequestCondition;
import org.springframework.web.reactive.result.condition.RequestMethodsRequestCondition;
import org.springframework.web.reactive.result.method.RequestMappingInfo;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;
import org.titan.argus.plugin.fallback.common.core.ArgusUrlMappingsRepository;
import org.titan.argus.plugin.fallback.common.entities.ArgusUrlMapping;
import org.titan.argus.plugin.fallback.hystrix.core.ArgusHystrixProperties;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author starboyate
 */
public class ArgusHystrixReactiveUrlMappingsRepository implements ArgusUrlMappingsRepository<ArgusHystrixProperties> {
	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;

	private Map<String, ArgusUrlMapping<ArgusHystrixProperties>> cache;

	private Set<ArgusUrlMapping<ArgusHystrixProperties>> fallbackCache;

	public void init() {
		Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
		this.cache = new ConcurrentHashMap<>(map.size());
		this.fallbackCache = new HashSet<>();
		for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
			RequestMappingInfo info = m.getKey();
			HandlerMethod method = m.getValue();
			PatternsRequestCondition p = info.getPatternsCondition();
			String url = null;
			for (PathPattern tempUrl : p.getPatterns()) {
				url = tempUrl.toString();
			}
			RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
			String type = methodsCondition.toString();
			if (type.startsWith("[") && type.endsWith("]")) {
				type = type.substring(1, type.length() - 1);
			}
			Method item  = method.getMethod();
			boolean isFallback = item.getAnnotation(HystrixCommand.class) != null || item.getAnnotation(
					HystrixCollapser.class) != null;
			if (!StringUtils.isBlank(type)) {
				ArgusUrlMapping<ArgusHystrixProperties> build = ArgusUrlMapping.<ArgusHystrixProperties>builder()
						.methodName(item.getName()).className(item.getDeclaringClass().getName()).type(type).parameterTypes(item.getParameterTypes()).url(url)
						.isFallback(isFallback).build();
				this.cache.put(item.getName(), build);
				if (isFallback) {
					this.fallbackCache.add(build);
				}
			}
		}
	}

	@Override
	public Map<String, ArgusUrlMapping<ArgusHystrixProperties>> getAllUrlMappings() {
		return this.cache;
	}

	@Override
	public ArgusHystrixProperties setFallbackProperties(ArgusHystrixProperties argusHystrixCommandProperties, String methodName) {
		ArgusUrlMapping<ArgusHystrixProperties> urlMapping = this.cache
				.get(methodName);
		Validate.notNull(urlMapping, "failed to get ArgusUrlMapping from key: %s", methodName);
		urlMapping.setFallbackProperties(argusHystrixCommandProperties);
		return argusHystrixCommandProperties;
	}

	public Set<ArgusUrlMapping<ArgusHystrixProperties>> getFallbackCache() {
		return this.fallbackCache;
	}
}
