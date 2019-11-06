package org.titan.argus.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class OauthController {
	@GetMapping("user")
	public Principal currentUser(Principal principal) {
		return principal;
	}
}
