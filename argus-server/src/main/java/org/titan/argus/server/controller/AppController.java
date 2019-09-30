package org.titan.argus.server.controller;

import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.titan.argus.model.vo.AppVO;
import org.titan.argus.server.response.ObjectCollectionResponse;
import org.titan.argus.service.AppService;

import java.util.ArrayList;


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
	public ObjectCollectionResponse<AppVO> findAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page, @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
		ArrayList<AppVO> list = Lists.newArrayList();
		ModelMapper modelMapper = new ModelMapper();
		appService.findAll().forEach(item -> list.add(modelMapper.map(item, AppVO.class)));
		return new ObjectCollectionResponse<>(list);
	}
}
