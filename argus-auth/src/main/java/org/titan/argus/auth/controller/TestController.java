package org.titan.argus.auth.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.*;
import org.titan.argus.auth.service.UserService;
import org.titan.argus.common.exception.ArgusAuthException;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @author starboyate
 */
@RestController
public class TestController {
	@Autowired
	private ConsumerTokenServices consumerTokenServices;

	@Autowired
	private UserService userService;

	@GetMapping("oauth/test")
	public String testOauth() {
		return "oauth";
	}

	@GetMapping("user")
	public Principal currentUser(Principal principal) {
		return principal;
	}

	@DeleteMapping("signout")
	public String signout(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		String token = StringUtils.replace(authorization, "bearer ", "");
		if (!consumerTokenServices.revokeToken(token)) {
			throw new ArgusAuthException("退出登录失败");
		}
		return "退出登录成功";
	}

//	@PostMapping("/oauth/add")
//	public String addUser(@RequestBody AddUserRequest addUserRequest) {
//		ModelMapper modelMapper = new ModelMapper();
//		User user = new User();
//		modelMapper.map(addUserRequest, user);
//		userService.addUser(user, addUserRequest.getRoleIds());
//		return "success";
//	}
//
//	@PostMapping("/add")
//	public String add(@RequestBody AddUserRequest addUserRequest) {
//		ModelMapper modelMapper = new ModelMapper();
//		User user = new User();
//		modelMapper.map(addUserRequest, user);
//		userService.addUser(user, addUserRequest.getRoleIds());
//		return "success";
//	}
}
