package org.titan.argus.service;

import org.titan.argus.discovery.common.entities.ArgusInstance;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author starboyate
 */
public interface InstanceService {

	 List<ArgusInstance> getInstanceByAppName(String appName);

	 ArgusInstance getInstanceById(String id);

	 Set<ArgusInstance> findAll();

	 List<ArgusInstance> page(int fromIndex, int toIndex);
}
