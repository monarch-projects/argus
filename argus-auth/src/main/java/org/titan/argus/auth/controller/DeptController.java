package org.titan.argus.auth.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.titan.argus.auth.model.Dept;
import org.titan.argus.auth.request.DeptRequest;
import org.titan.argus.auth.service.DeptService;
import org.titan.argus.common.response.ObjectDataResponse;


/**
 * @author starboyate
 */
@RestController
@RequestMapping("/api/v1/dept")
public class DeptController {
	@Autowired
	private DeptService deptService;

	@GetMapping("/tree")
	public ObjectDataResponse deptTree() {
		return new ObjectDataResponse<>(this.deptService.findDeptsTree());
	}

	@PutMapping
	@PreAuthorize("hasAnyAuthority('dept-update') or hasAnyAuthority('*')")
	public ObjectDataResponse updateDept(@RequestBody DeptRequest request) {
		ModelMapper modelMapper = new ModelMapper();
		Dept dept = modelMapper.map(request, Dept.class);
		this.deptService.updateDept(dept);
		return new ObjectDataResponse();
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('dept-add') or hasAnyAuthority('*')")
	public ObjectDataResponse addDept(@RequestBody DeptRequest request) {
		ModelMapper modelMapper = new ModelMapper();
		Dept dept = modelMapper.map(request, Dept.class);
		this.deptService.addDept(dept);
		return new ObjectDataResponse();
	}
}
