package org.titan.argus.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.titan.argus.model.entities.InstanceMetadata;
import org.titan.argus.model.vo.InstanceVO;
import org.titan.argus.server.response.ObjectCollectionResponse;
import org.titan.argus.server.response.ObjectDataResponse;
import org.titan.argus.service.RedisService;
import org.titan.argus.storage.es.service.RedisMonitorNodeInfoService;
import org.titan.argus.tools.monitor.redis.core.RedisNodeHolder;
import org.titan.argus.tools.monitor.redis.domain.RedisNode;
import org.titan.argus.util.PageUtil;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author starboyate
 */
@RestController
@RequestMapping("/api/v1/redis")
@Api(value = "监控项目redis接口", tags = {"监控项目redis接口"})
public class RedisController extends BaseController{
	@Autowired
	private RedisService redisService;

	@Autowired
	private RedisMonitorNodeInfoService redisMonitorNodeInfoService;

	@ApiOperation(value = "获取redis监控指标", notes = "动态获取项目使用的redis的监控信息")
	@GetMapping("/{id}/metric")
	public ObjectDataResponse getRedisMetricInfo(@PathVariable Long id) {
		RedisNode redisNode = RedisNodeHolder.get(id);
		return new ObjectDataResponse<>(redisService.getRedisMetricInfo(redisNode));
	}

	@GetMapping("/node")
	public ObjectCollectionResponse getRedisNodeInfo() {
		return new ObjectCollectionResponse<>(redisService.getRedisNodeList());
	}


	@ApiOperation(value = "获取redis所有的支持动态调整的config", notes = "动态获取项目使用的redis的所有的支持动态调整的config配置")
	@GetMapping("/{id}/config")
	public ObjectDataResponse getRedisAllConfig(@PathVariable Long id) {
		RedisNode redisNode = RedisNodeHolder.get(id);
		return new ObjectDataResponse<>(redisService.getRedisConfig(redisNode));
	}

	@ApiOperation(value = "动态修改redis的config", notes = "动态调整修改redis的config")
	@ApiImplicitParam(name = "configMap", value = "动态修改的redis参数", required = true, paramType = "query", dataType = "Map<String, String>")
	@PutMapping("/{id}/config")
	public ObjectDataResponse updateRedisConfig(@PathVariable Long id, @RequestBody Map<String, String> configMap) {
		RedisNode redisNode = RedisNodeHolder.get(id);
		return new ObjectDataResponse<>(redisService.setConfig(redisNode, configMap));
	}

	@GetMapping("/history/metric")
	public ObjectCollectionResponse getRedisMetricByIp(
			@RequestParam(value = "ip") String ip,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size) {
		return new ObjectCollectionResponse<>(this.redisMonitorNodeInfoService.findByIp(ip, page, size));
	}



}
