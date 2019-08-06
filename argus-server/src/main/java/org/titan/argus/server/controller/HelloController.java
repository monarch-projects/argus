package org.titan.argus.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.titan.argus.discovery.eureka.repository.ArgusEurekaInstanceRepository;

import java.util.Map;

/**
 * @author starboyate
 */
@RestController
public class HelloController {
	@Autowired
	private ArgusEurekaInstanceRepository repository;

	@GetMapping("/hello")
	public Map hello() {
		return repository.findAll();
	}
}
