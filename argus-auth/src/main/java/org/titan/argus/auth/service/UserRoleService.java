package org.titan.argus.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.titan.argus.auth.model.UserRole;

import java.util.List;

/**
 * @author starboyate
 */
public interface UserRoleService extends IService<UserRole> {
	void addUserRole(UserRole userRole);

	void updateUserRoleByUserId(Long userId, List<Long> roleIds);

	void delUserRole(Long id);

	void delUserRoles(List<Long> ids);

	void addUserRoles(List<UserRole> list);

}
