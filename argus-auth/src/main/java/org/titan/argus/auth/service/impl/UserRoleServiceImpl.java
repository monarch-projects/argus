package org.titan.argus.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.titan.argus.auth.mapper.UserRoleMapper;
import org.titan.argus.auth.model.UserRole;
import org.titan.argus.auth.service.UserRoleService;

import java.util.List;

/**
 * @author starboyate
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

	@Autowired
	private UserRoleMapper userRoleMapper;

	@Override
	public void addUserRole(UserRole userRole) {
		this.userRoleMapper.insert(userRole);
	}

	@Override
	public void updateUserRoleByUserId(Long userId, List<Long> roleIds) {
		roleIds.forEach(item -> {
			this.baseMapper.update(null, new UpdateWrapper<UserRole>().set("role_id", item).eq("user_id", userId));
		});
	}


	@Override
	public void delUserRole(Long id) {
		this.userRoleMapper.deleteById(id);
	}

	@Override
	public void delUserRoles(List<Long> ids) {
		this.userRoleMapper.deleteBatchIds(ids);
	}

	@Override
	public void addUserRoles(List<UserRole> list) {
		saveBatch(list);
	}
}
