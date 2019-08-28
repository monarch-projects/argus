package org.titan.argus.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.titan.argus.model.entities.InstanceMetadata;
import org.titan.argus.model.response.BaseResponse;
import org.titan.argus.server.core.ArgusActuatorConstant;

import java.util.Map;
import java.util.Set;

/**
 * @author starboyate
 */
@RestController
@RequestMapping("/redis")
@Api(value = "监控项目redis接口", tags = {"监控项目redis接口"})
public class RedisController extends BaseController{
	private InstanceMetadata metadata;

	@ApiOperation(value = "获取redis所有的系统信息", notes = "动态获取项目使用的redis的具体系统信息")
	@GetMapping("/info")
	public BaseResponse getRedisAllInfo() {
		before();
		return proxyGet(ArgusActuatorConstant.REDIS_INFO, metadata.getId());
	}


	@ApiOperation(value = "获取redis内存使用情况", notes = "动态获取项目使用的redis的已经使用的内存大小")
	@GetMapping("/used/memory")
	public BaseResponse getRedisUsedMemory() {
		before();
		return proxyGet(ArgusActuatorConstant.REDIS_USED_MEMORY, this.metadata.getId());
	}

	@ApiOperation(value = "获取redis所有的key-value键值对信息", notes = "动态获取项目使用的redis的所有的存储的key-value键值对信息")
	@GetMapping("/resources")
	public BaseResponse getRedisAllResources() {
		before();
		return proxyGet(ArgusActuatorConstant.REDIS_RESOURCES, this.metadata.getId());
	}

	@ApiOperation(value = "获取redis所有的支持动态调整的config", notes = "动态获取项目使用的redis的所有的支持动态调整的config配置")
	@GetMapping("/config")
	public BaseResponse getRedisAllConfig() {
		before();
		return proxyGet(ArgusActuatorConstant.REDIS_CONFIG, this.metadata.getId());
	}

	@ApiOperation(value = "动态修改redis的config", notes = "动态调整修改redis的config")
	@ApiImplicitParam(name = "configMap", value = "动态修改的redis参数", required = true, paramType = "query", dataType = "Map<String, String>")
	@PutMapping("/config")
	public BaseResponse updateRedisConfig(@RequestBody Map<String, String> configMap) {
		before();
		return proxyPut(ArgusActuatorConstant.REDIS_CONFIG, this.metadata.getId(), configMap);
	}

	private void before() {
		Set<InstanceMetadata> allInstanceMetadata = getAllInstanceMetadata();
		for (InstanceMetadata metadata : allInstanceMetadata) {
			if (metadata.getIsUsedRedis()) {
				this.metadata = metadata;
			}
		}
	}
}
