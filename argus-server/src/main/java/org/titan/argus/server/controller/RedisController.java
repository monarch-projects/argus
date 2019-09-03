package org.titan.argus.server.controller;

import com.google.common.collect.Sets;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.titan.argus.model.entities.InstanceMetadata;
import org.titan.argus.server.core.ArgusActuatorConstant;
import org.titan.argus.server.core.MiddleWareNodeHolder;
import org.titan.argus.server.response.ObjectCollectionResponse;
import org.titan.argus.server.response.ObjectDataResponse;
import java.util.Map;
import java.util.Set;

/**
 * @author starboyate
 */
@RestController
@RequestMapping("/api/v1/redis")
@Api(value = "监控项目redis接口", tags = {"监控项目redis接口"})
public class RedisController extends BaseController{
	private final Set<InstanceMetadata> metadataSet = Sets.newHashSet();


	@ApiOperation(value = "获取redis所有的系统信息", notes = "动态获取项目使用的redis的具体系统信息")
	@GetMapping("/{id}/info")
	public ObjectDataResponse getRedisAllInfo(@PathVariable String id) {
		InstanceMetadata metadata = this.metadataSet.stream().filter(item -> item.getId().equals(id)).findFirst()
				.orElse(null);
		return proxyGet(ArgusActuatorConstant.REDIS_INFO, metadata.getId());
	}

	@GetMapping("/node")
	public ObjectCollectionResponse getRedisNodeInfo() {
		return new ObjectCollectionResponse<>(MiddleWareNodeHolder.getRedisNodeInfoSet());
	}


	@ApiOperation(value = "获取redis内存使用情况", notes = "动态获取项目使用的redis的已经使用的内存大小")
	@GetMapping("/{id}/used/memory")
	public ObjectDataResponse getRedisUsedMemory(@PathVariable String id) {
		InstanceMetadata metadata = this.metadataSet.stream().filter(item -> item.getId().equals(id)).findFirst()
				.orElse(null);
		return proxyGet(ArgusActuatorConstant.REDIS_USED_MEMORY, metadata.getId());
	}

	@ApiOperation(value = "获取redis所有的key-value键值对信息", notes = "动态获取项目使用的redis的所有的存储的key-value键值对信息")
	@GetMapping("/{id}/resources")
	public ObjectDataResponse getRedisAllResources(@PathVariable String id) {
		InstanceMetadata metadata = this.metadataSet.stream().filter(item -> item.getId().equals(id)).findFirst()
				.orElse(null);
		return proxyGet(ArgusActuatorConstant.REDIS_RESOURCES, metadata.getId());
	}

	@ApiOperation(value = "获取redis所有的支持动态调整的config", notes = "动态获取项目使用的redis的所有的支持动态调整的config配置")
	@GetMapping("/{id}/config")
	public ObjectDataResponse getRedisAllConfig(@PathVariable String id) {
		InstanceMetadata metadata = this.metadataSet.stream().filter(item -> item.getId().equals(id)).findFirst()
				.orElse(null);
		return proxyGet(ArgusActuatorConstant.REDIS_CONFIG, metadata.getId());
	}

	@ApiOperation(value = "动态修改redis的config", notes = "动态调整修改redis的config")
	@ApiImplicitParam(name = "configMap", value = "动态修改的redis参数", required = true, paramType = "query", dataType = "Map<String, String>")
	@PutMapping("/{id}/config")
	public ObjectDataResponse updateRedisConfig(@PathVariable String id, @RequestBody Map<String, String> configMap) {
		InstanceMetadata metadata = this.metadataSet.stream().filter(item -> item.getId().equals(id)).findFirst()
				.orElse(null);
		return proxyPut(ArgusActuatorConstant.REDIS_CONFIG, metadata.getId(), configMap);
	}

}
