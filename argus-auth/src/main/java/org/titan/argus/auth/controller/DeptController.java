package org.titan.argus.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.titan.argus.auth.model.Dept;
import org.titan.argus.auth.request.DeptRequest;
import org.titan.argus.auth.service.DeptService;
import org.titan.argus.auth.vo.DeptVO;
import org.titan.argus.auth.vo.Tree;
import org.titan.argus.common.response.ObjectDataResponse;

import java.util.List;


/**
 * @author starboyate
 */
@RestController
@RequestMapping("/api/v1/dept")
@Api(value = "部门信息管理接口", tags = {"部门信息管理接口"})
public class DeptController {
	@Autowired
	private DeptService deptService;

	@GetMapping("/tree")
	@ApiOperation(value = "获取所有部门tree", notes = "获取所有的部门信息并且以树结构展示")
	public ObjectDataResponse<DeptVO> deptTree() {
		return new ObjectDataResponse<>((DeptVO)this.deptService.findDeptsTree());
	}

	@PutMapping
	@ApiOperation(value = "修改部门信息", notes = "获取所有的部门信息并且以树结构展示")
	@PreAuthorize("hasAnyAuthority('dept-update')")
	public ObjectDataResponse updateDept(@RequestBody DeptRequest request) {
		ModelMapper modelMapper = new ModelMapper();
		Dept dept = modelMapper.map(request, Dept.class);
		this.deptService.updateDept(dept);
		return new ObjectDataResponse();
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('dept-add') or hasAnyAuthority('*')")
	@ApiOperation(value = "添加部门", notes = "获取所有的部门信息并且以树结构展示")
	public ObjectDataResponse addDept(@RequestBody DeptRequest request) {
		ModelMapper modelMapper = new ModelMapper();
		Dept dept = modelMapper.map(request, Dept.class);
		this.deptService.addDept(dept);
		return new ObjectDataResponse();
	}
}
