package org.titan.argus.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.titan.argus.auth.request.RoleRequest;
import org.titan.argus.auth.service.RoleService;
import org.titan.argus.common.response.ObjectDataResponse;

/**
 * @author starboyate
 */
@RestController
@RequestMapping("/api/v1/role")
@Api(value = "角色信息管理接口", tags = {"角色信息管理接口"})
public class RoleController {
	@Autowired
	private RoleService roleService;

	@ApiOperation(value = "查询角色信息", notes = "查询所有角色信息分页显示")
	@GetMapping
	@PreAuthorize("hasAnyAuthority('role-view')")
	public ObjectDataResponse getAllRole(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size,
			RoleRequest roleRequest) {
		return new ObjectDataResponse<>(this.roleService.findRoles(roleRequest));
	}

	@ApiOperation(value = "修改角色信息", notes = "修改角色信息")
	@PutMapping
	@PreAuthorize("hasAnyAuthority('role-update')")
	public ObjectDataResponse modifyRole(RoleRequest request) {
		this.roleService.updateRole(request);
		return new ObjectDataResponse();
	}

	@ApiOperation(value = "添加角色信息", notes = "添加角色信息")
	@PostMapping
	@PreAuthorize("hasAnyAuthority('role-add')")
	public ObjectDataResponse addRole(RoleRequest request) {
		this.roleService.addRole(request);
		return new ObjectDataResponse();
	}

}
