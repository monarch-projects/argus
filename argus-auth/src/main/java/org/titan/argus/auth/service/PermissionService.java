package org.titan.argus.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.titan.argus.auth.model.Permission;
import org.titan.argus.auth.vo.Tree;
import org.titan.argus.auth.vo.UserPermissionVO;

import java.util.List;

public interface PermissionService extends IService<Permission> {
	List<String> findUserPointByUserName(String userName);

	List<Permission> findUserMenuByUserName(String userName);

	UserPermissionVO findPermissionsByUserName(String username);

	UserPermissionVO findAllPermission();

	List<? extends Tree> findPermissionTree();
}
