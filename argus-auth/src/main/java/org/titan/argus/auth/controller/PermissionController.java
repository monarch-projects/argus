package org.titan.argus.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.titan.argus.auth.model.Role;
import org.titan.argus.auth.service.PermissionService;
import org.titan.argus.auth.service.RoleService;
import org.titan.argus.auth.vo.UserPermissionVO;
import org.titan.argus.common.response.ObjectCollectionResponse;
import org.titan.argus.common.response.ObjectDataResponse;

import java.security.Principal;
import java.util.List;

/**
 * @author starboyate
 */
@RestController
@RequestMapping("/api/v1/permission")
public class PermissionController {
	@Autowired
	private PermissionService permissionService;

	@Autowired
	private RoleService roleService;

	@GetMapping
	public ObjectDataResponse<UserPermissionVO> findUserPermission(Principal principal) {
		String username = principal.getName();
		List<Role> roleList = this.roleService.findRoleByUserName(username);
		Role admin = roleList.stream().filter(item -> item.getName().equalsIgnoreCase("admin")).findFirst()
				.orElse(null);
		UserPermissionVO userPermissionVO;
		if (admin != null) {
			userPermissionVO = this.permissionService.findAllPermission();
		} else {
			userPermissionVO = this.permissionService.findPermissionsByUserName(username);
		}

		return new ObjectDataResponse<>(userPermissionVO);
	}

	@GetMapping("/tree")
	public ObjectCollectionResponse permissionTree() {
		return new ObjectCollectionResponse<>(this.permissionService.findPermissionTree());
	}

}
