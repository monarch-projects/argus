package org.titan.argus.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.titan.argus.auth.model.Permission;
import org.titan.argus.auth.vo.Tree;
import org.titan.argus.auth.vo.UserPermissionVO;

import java.util.List;

public interface PermissionService extends IService<Permission> {
	List<String> findUserPointByUserName(String username);

	UserPermissionVO findPermissionsByUserName(String username);

	UserPermissionVO findAllPermission();

	List<? extends Tree> findPermissionTree();
}
