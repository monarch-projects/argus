package org.titan.argus.server.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.titan.argus.model.entities.InstanceMetadata;
import org.titan.argus.model.request.RouteRequest;
import org.titan.argus.server.core.ArgusActuatorConstant;
import org.titan.argus.server.response.ObjectDataResponse;

import java.util.Date;
import java.util.Set;


/**
 * @author starboyate
 */
@RestController
@RequestMapping("/api/v1/route")
@Api(value = "动态操作路由规则的接口", tags = {"动态操作路由规则的接口"})
public class RouteController extends BaseController {
	private InstanceMetadata metadata;

	@ApiOperation(value = "获取网关的所有配置的路由规则", notes = "动态获取项目使用的网关的具体路由信息")
	@GetMapping
	public ObjectDataResponse getAllRoutes() {
		return proxyGet(ArgusActuatorConstant.ROUTE, metadata.getId());
	}

	@ApiOperation(value = "动态修改网关的路由规则", notes = "动态修改网关的路由规则")
	@ApiImplicitParam(name = "routeVO", value = "修改的路由信息", required = true, paramType = "query", dataType = "RouteVO")
	@PutMapping("/{id}")
	public ObjectDataResponse updateRoute(@PathVariable String id, @RequestBody RouteRequest routeRequest) {
		return proxyPut(ArgusActuatorConstant.ROUTE + id, metadata.getId(), routeRequest);
	}


}
