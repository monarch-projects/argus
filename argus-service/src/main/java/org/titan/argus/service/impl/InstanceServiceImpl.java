package org.titan.argus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.titan.argus.discovery.common.entities.ArgusInstance;
import org.titan.argus.discovery.common.repository.InstanceRepository;
import org.titan.argus.service.InstanceService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author starboyate
 */
@Service
public class InstanceServiceImpl implements InstanceService {
	@Autowired
	private InstanceRepository repository;

	@Override
	public List<ArgusInstance> getInstanceByAppName(String appName) {
		return repository.getInstanceByAppName(appName);
	}

	@Override
	public ArgusInstance getInstanceById(String id) {
		return this.repository.getInstanceById(id);
	}

	@Override
	public Set<ArgusInstance> findAll() {
		return this.repository.findAll();
	}

}
