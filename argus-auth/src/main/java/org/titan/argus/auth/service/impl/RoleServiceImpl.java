package org.titan.argus.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.titan.argus.auth.mapper.RoleMapper;
import org.titan.argus.auth.model.Role;
import org.titan.argus.auth.model.RolePermission;
import org.titan.argus.auth.request.RoleRequest;
import org.titan.argus.auth.service.RolePermissionService;
import org.titan.argus.common.exception.BusinessException;
import org.titan.argus.auth.service.RoleService;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author starboyate
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

	@Autowired
	private RolePermissionService rolePermissionService;

	@Override
	public List<Role> findRoleByUserName(String userName) {
		return this.baseMapper.getRolesByUserName(userName);
	}

	@Override
	public List<Role> findRoles(RoleRequest roleRequest) {
		QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNotBlank(roleRequest.getName())) {
			queryWrapper.eq("name", roleRequest.getName());
		}
		return this.baseMapper.selectList(queryWrapper);
	}

	@Override
	public void updateRole(RoleRequest request) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			Role role = modelMapper.map(request, Role.class);
			this.baseMapper.updateById(role);
			if (CollectionUtils.isNotEmpty(request.getPermissions())) {
				this.rolePermissionService.updatePermissionsByRoleId(request.getId(), request.getPermissions());
			}
		} catch (Exception ex) {
			throw new BusinessException("角色修改异常: " + ex.getMessage());
		}
	}

	@Override
	@Transactional
	public void addRole(RoleRequest request) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			Role role = modelMapper.map(request, Role.class);
			this.baseMapper.insert(role);
			if (CollectionUtils.isNotEmpty(request.getPermissions())) {
				List<RolePermission> rolePermissions = request.getPermissions().stream()
						.map(item -> RolePermission.builder().roleId(request.getId()).permissionId(item).build())
						.collect(Collectors.toList());
				this.rolePermissionService.saveBatch(rolePermissions);
			}
		} catch (Exception ex) {
			throw new BusinessException("添加角色异常: " + ex.getMessage());
		}
	}
}
