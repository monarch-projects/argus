package org.titan.argus.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.titan.argus.auth.model.Role;
import org.titan.argus.auth.service.PermissionService;
import org.titan.argus.auth.service.RoleService;
import org.titan.argus.auth.vo.PermissionVO;
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
@Api(value = "资源权限信息管理接口", tags = {"资源权限信息管理接口"})
public class PermissionController {
	@Autowired
	private PermissionService permissionService;

	@Autowired
	private RoleService roleService;

	@GetMapping
	@ApiOperation(value = "查询用户权限", notes = "查询当前登录用户的所有权限")
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
	@ApiOperation(value = "获取所有资源权限tree", notes = "获取所有资源权限信息并且以树结构展示")
	public ObjectCollectionResponse<PermissionVO> permissionTree() {
		return new ObjectCollectionResponse<>((List<PermissionVO>)this.permissionService.findPermissionTree());
	}

}
