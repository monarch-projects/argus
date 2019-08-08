package org.titan.argus.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.titan.argus.discovery.common.entities.ArgusInstance;
import org.titan.argus.service.InstanceService;

import java.util.List;
import java.util.Map;

/**
 * @author starboyate
 */
@RestController
public class InstanceController {
	@Autowired
	private InstanceService instanceService;

	@GetMapping("/instances")
	public Map<String, List<ArgusInstance>> findAll() {
		return instanceService.findAll();
	}

	@GetMapping("/instance/{appName}")
	public List<ArgusInstance> getInstanceByAppName(@PathVariable String appName) {
		return instanceService.getInstanceByAppName(appName);
	}
}
