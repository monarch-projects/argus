package org.titan.argus.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.titan.argus.auth.model.Role;
import org.titan.argus.auth.request.RoleRequest;

import java.util.List;

/**
 * @author starboyate
 */
public interface RoleService extends IService<Role> {
	List<Role> findRoleByUserName(String userName);

	List<Role> findRoles(RoleRequest roleRequest);

	void updateRole(RoleRequest request);

	void addRole(RoleRequest request);
}
