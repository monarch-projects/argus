package org.titan.argus.service;

import org.titan.argus.discovery.common.entities.ArgusInstance;

import java.util.List;
import java.util.Map;

/**
 * @author starboyate
 */
public interface InstanceService {
	 Map<String, List<ArgusInstance>> findAll();

	 List<ArgusInstance> getInstanceByAppName(String appName);
}
