package org.titan.argus.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.titan.argus.auth.mapper.RolePermissionMapper;
import org.titan.argus.auth.model.RolePermission;
import org.titan.argus.auth.service.RolePermissionService;

import java.util.List;

/**
 * @author starboyate
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {
	@Override
	public void updatePermissionsByRoleId(Long roleId, List<Long> permissionIds) {
		permissionIds.forEach(item -> {
			this.baseMapper.update(null, new UpdateWrapper<RolePermission>().set("permission_id", item).eq("role_id", roleId));
		});
	}
}
