package org.titan.argus.plugin.fallback.common.core;

import org.titan.argus.plugin.fallback.common.entities.ArgusUrlMapping;

import java.util.Map;

/**
 * @author starboyate
 */
public interface ArgusUrlMappingsRepository<T> {
	Map<String, ArgusUrlMapping<T>> getAllUrlMappings();

	T setFallbackProperties(T t, String methodName);
}
