package org.titan.argus.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.titan.argus.auth.model.RolePermission;

import java.util.List;

/**
 * @author starboyate
 */
public interface RolePermissionService extends IService<RolePermission> {
	void updatePermissionsByRoleId(Long roleId, List<Long> permissionIds);
}
