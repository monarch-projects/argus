package org.titan.argus.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.titan.argus.discovery.eureka.repository.ArgusEurekaAppRepository;
import org.titan.argus.discovery.eureka.repository.ArgusEurekaInstanceRepository;

import java.util.Map;

/**
 *
 */
@RestController
public class HelloController {

	@Autowired
	private ArgusEurekaAppRepository argusEurekaAppRepository;

	@Autowired
	private ArgusEurekaInstanceRepository argusEurekaInstanceRepository;

	@GetMapping("/hello")
	public Map hello() {
		return argusEurekaInstanceRepository.getAllInstances();
	}

	@GetMapping("/app")
	public Map app() {
		return argusEurekaAppRepository.getAllApp();
	}
}
