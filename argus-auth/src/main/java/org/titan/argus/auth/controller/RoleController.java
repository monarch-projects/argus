package org.titan.argus.auth.controller;

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
public class RoleController {
	@Autowired
	private RoleService roleService;

	@GetMapping
	@PreAuthorize("hasAnyAuthority('role-view') or hasAnyAuthority('*')")
	public ObjectDataResponse getAllRole(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size,
			RoleRequest roleRequest) {
		return new ObjectDataResponse<>(this.roleService.findRoles(roleRequest));
	}

	@PutMapping
	@PreAuthorize("hasAnyAuthority('role-update') or hasAnyAuthority('*')")
	public ObjectDataResponse modifyRole(RoleRequest request) {
		this.roleService.updateRole(request);
		return new ObjectDataResponse();
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('role-add') or hasAnyAuthority('*')")
	public ObjectDataResponse addRole(RoleRequest request) {
		this.roleService.addRole(request);
		return new ObjectDataResponse();
	}

}
