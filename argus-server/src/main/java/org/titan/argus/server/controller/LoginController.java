package org.titan.argus.server.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.titan.argus.server.response.ObjectDataResponse;

/**
 * @author starboyate
 */
@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

	@PostMapping
	public ObjectDataResponse login() {

	}
}
