package org.titan.argus.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.titan.argus.service.AuthService;


/**
 * @author starboyate
 */
@RestController
@Api(value = "认证授权操作接口", tags = {"认证授权操作接口"})
public class AuthController extends BaseController {
	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	/**
	 * 登录
	 */
	@ApiOperation(value = "登录", notes = "根据用户的账号和密码进行登录，登录成功返回TOKEN")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "username", value = "账号", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query", dataType = "String")
	})
	@PostMapping(value = "/auth/login")
	public String login(String username,String password) {
		// 登录成功会返回Token给用户
		return authService.login(username, password);
	}
}
