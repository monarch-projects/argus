package org.titan.argus.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.titan.argus.model.response.BaseResponse;
import org.titan.argus.service.AppService;


/**
 * @author starboyate
 */
@RestController
@RequestMapping("/api/v1/apps")
@Api(value = "注册中心app接口", tags = {"注册中心app接口"})
public class AppController extends BaseController {
	private final AppService appService;

	public AppController(AppService appService) {
		this.appService = appService;
	}


	@GetMapping
	@ApiOperation(value = "获取注册中心所有的app", notes = "根据使用的注册中心进行获取所有注册的application")
	public BaseResponse findAll() {
		return BaseResponse.success(appService.findAll());
	}
}
