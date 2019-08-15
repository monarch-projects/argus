package org.titan.argus.plugin.fallback.common.core;

import org.titan.argus.plugin.fallback.common.entities.ArgusUrlMapping;

import java.util.Set;

/**
 * @author starboyate
 */
public interface ArgusUrlMappingsRepository {
	Set<ArgusUrlMapping> getAllUrlMappings();
}
