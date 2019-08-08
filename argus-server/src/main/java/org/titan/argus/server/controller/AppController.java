package org.titan.argus.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.titan.argus.discovery.common.entities.ArgusServiceApp;
import org.titan.argus.service.AppService;

import java.util.Map;

/**
 * @author starboyate
 */
@RestController
public class AppController {
	@Autowired
	private AppService appService;

	@GetMapping("/app")
	public Map<String, ArgusServiceApp> findAll() {
		return appService.findAll();
	}
}
