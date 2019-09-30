package org.titan.argus.server.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.titan.argus.discovery.common.entities.ArgusInstance;
import org.titan.argus.model.entities.InstanceMetadata;
import org.titan.argus.model.request.ArgusUrlMappingRequest;
import org.titan.argus.model.vo.InstanceMetadataVO;
import org.titan.argus.model.vo.InstanceVO;
import org.titan.argus.server.core.ArgusActuatorConstant;
import org.titan.argus.server.response.ObjectCollectionResponse;
import org.titan.argus.server.response.ObjectDataResponse;
import org.titan.argus.service.exception.BusinessException;
import org.titan.argus.util.PageUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author starboyate
 */
@RestController
@RequestMapping("/api/v1/instances")
@Api(value = "服务实例操作接口", tags = {"服务实例操作接口"})
public class InstanceController extends BaseController {


	@ApiOperation(value = "获取所有的服务实例进行分页", notes = "从注册中心上分页查询服务实例信息")
	@GetMapping
	public ObjectCollectionResponse<InstanceVO> findAll(
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size) {
		Set<InstanceMetadata> allInstanceMetadata = this.holder.getAllInstanceMetadata();
		Set<InstanceVO> collect = allInstanceMetadata.stream()
				.map(item -> InstanceVO.builder().appName(item.getAppName()).id(item.getId()).status(item.getStatus())
						.build()).collect(Collectors.toSet());
		return new ObjectCollectionResponse<>(PageUtil.page(page, size, collect));
	}

	@GetMapping("/{appName}")
	@ApiOperation(value = "获取某个app下的所有服务实例", notes = "通过应用名称获取该应用下的所有服务实例信息")
	public ObjectCollectionResponse<InstanceVO> getInstanceByAppName(
			@PathVariable String appName,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size) {
		List<InstanceVO> collect = instanceService.getInstanceByAppName(appName).stream()
				.map(item -> InstanceVO.builder().status(item.getStatus()).id(item.getId()).appName(item.getAppName())
						.build()).collect(Collectors.toList());
		return new ObjectCollectionResponse<>(PageUtil.page(page, size, collect));
	}

	@ApiOperation(value = "获取指定服务详细信息(元数据)", notes = "通过服务实例ID来获取服务实例详细信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "实例ID(instance id not app id)", required = true, paramType = "query", dataType = "String")
	})
	@GetMapping("/{id}/metadata")
	public ObjectDataResponse<InstanceMetadataVO> getMetadata(@PathVariable String id) {
		InstanceMetadata instanceMetadata = this.holder.getAllInstanceMetadata().stream().filter(metadata -> metadata.getId().equals(id))
				.findFirst().orElse(null);
		InstanceMetadataVO metadataVO;
		if (null == instanceMetadata) {
			ArgusInstance instance = this.instanceService.getInstanceById(id);
			String url = getUrl(instance.getHomePageUrl(), ArgusActuatorConstant.META_INFO);
			String entity = null;
			try {
				entity = httpClient.doGet(url);
			} catch (Exception ex) {
				throw new BusinessException(ex.getMessage());
			}
			metadataVO = JSON.parseObject(entity, InstanceMetadataVO.class);
			metadataVO.setAppName(instance.getAppName());
			metadataVO.setEventMap(instance.getEventMap());
			metadataVO.setId(instance.getId());
			metadataVO.setId(instance.getId());
			metadataVO.setPort(instance.getPort());
			metadataVO.setStatus(instance.getStatus());
		} else {
			metadataVO = instanceMetadata.convertToInstanceMetadataVO();
		}
		return new ObjectDataResponse<>(metadataVO);
	}

	@ApiOperation(value = "获取指定服务实例的maven依赖信息", notes = "通过服务实例ID获取服务实例的maven依赖具体信息")
	@GetMapping("/{id}/dependency")
	public ObjectDataResponse getDependency(@PathVariable String id) {
		return proxyGet(ArgusActuatorConstant.DEPENDENCY, id);
	}

	@ApiOperation(value = "获取指定服务实例的traces信息", notes = "通过服务实例ID获取服务实例的链路调用信息")
	@GetMapping("/{id}/traces")
	public ObjectDataResponse getTraces(
			@PathVariable String id,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size) {
		Map<String, Object> map = new HashMap<>();
		map.put("page", page);
		map.put("size", size);
		return proxyGet(ArgusActuatorConstant.TRACES, id, map);
	}

	@ApiOperation(value = "获取指定服务实例的jvm信息", notes = "通过服务实例ID获取服务实例的jvm信息")
	@GetMapping("/{id}/jvm")
	public ObjectDataResponse getJvm(@PathVariable String id) {
		return proxyGet(ArgusActuatorConstant.JVM, id);
	}

	@ApiOperation(value = "动态修改指定服务实例的jvm参数", notes = "通过服务实例ID来动态调整jvm参数")
	@PutMapping("/{id}/jvm")
	public ObjectDataResponse dynamicChangeJvmArgus(@PathVariable String id, @RequestBody Map<String, String> map) {
		return proxyPut(ArgusActuatorConstant.JVM_ARGS, id, map);
	}

	@ApiOperation(value = "获取指定服务实例的url mappings信息", notes = "通过服务实例ID来获取url mappings信息")
	@GetMapping("/{id}/urlMappings")
	public ObjectDataResponse getUrlMappings(@PathVariable String id) {
		return proxyGet(ArgusActuatorConstant.URL_MAPPINGS, id);
	}

	@ApiOperation(value = "动态调整hystrix配置", notes = "动态调整hystrix配置")
	@PutMapping("/{id}/fallback")
	public ObjectDataResponse fallback(@PathVariable String id,@RequestBody ArgusUrlMappingRequest urlMappingRequest) {
		return proxyPut(ArgusActuatorConstant.FALLBAK, id, urlMappingRequest);
	}


}
