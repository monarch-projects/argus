package org.titan.argus.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.titan.argus.auth.constant.ArgusAuthConstant;
import org.titan.argus.auth.enums.UserStatus;
import org.titan.argus.auth.mapper.UserMapper;
import org.titan.argus.auth.model.*;
import org.titan.argus.auth.request.AddUserRequest;
import org.titan.argus.auth.request.UpdateUserRequest;
import org.titan.argus.auth.service.*;
import org.titan.argus.common.exception.AccountPresenceException;
import org.titan.argus.common.exception.BusinessException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author starboyate
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private PasswordEncoder passwordEncoder;


	@Autowired
	private UserDeptService userDeptService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private DeptService deptService;

	@Override
	@Transactional
	public User findByName(String userName) {
		User user = this.baseMapper.selectOne(new QueryWrapper<User>().eq("user_name", userName));
		List<Role> roleList = this.roleService.findRoleByUserName(userName);
		List<Dept> deptList = this.deptService.findDeptsByUserName(userName);
		user.setRoleList(roleList);
		user.setDeptList(deptList);
		return this.baseMapper.selectOne(new QueryWrapper<User>().eq("user_name", userName));
	}

	@Override
	@Transactional
	public void addUser(AddUserRequest request) {
		ModelMapper modelMapper = new ModelMapper();
		User user = modelMapper.map(request, User.class);
		boolean checkUserName = checkUserName(user.getUsername());
		if (checkUserName) {
			throw new AccountPresenceException("用户名已经存在");
		}
		user.setPassword(passwordEncoder.encode(ArgusAuthConstant.DEFAULT_PASSWORD));
		user.setStatus(UserStatus.AVAILABLE.getCode());
		this.baseMapper.insert(user);
		if (CollectionUtils.isNotEmpty(request.getRoles())) {
			List<UserRole> userRoles = request.getRoles().stream().map(item -> UserRole.builder().roleId(item).userId(user.getId()).build())
					.collect(Collectors.toList());
			this.userRoleService.addUserRoles(userRoles);
		}
		if (CollectionUtils.isNotEmpty(request.getDepts())) {
			List<UserDept> userDepts = request.getDepts().stream().map(item -> UserDept.builder().deptId(item).userId(user.getId()).build())
					.collect(Collectors.toList());
			this.userDeptService.addUserDepts(userDepts);
		}

	}

	@Override
	public void delUser(Long id) {
		this.baseMapper.deleteById(id);
	}

	@Override
	public void delUsers(List<Long> ids) {
		this.baseMapper.deleteBatchIds(ids);
	}

	@Override
	@Transactional
	public void updateUser(UpdateUserRequest request) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			User user = modelMapper.map(request, User.class);
			this.baseMapper.updateById(user);
			this.userDeptService.updateDeptsByUserId(request.getId(), request.getDepts());
			this.userRoleService.updateUserRoleByUserId(request.getId(), request.getRoles());
		} catch (Exception ex) {
			throw new BusinessException("修改用户异常: " + ex.getMessage());
		}
	}

	@Override
	public boolean checkUserName(String username) {
		return this.findByName(username) != null;
	}

	@Override
	public IPage<User> pageAllUser(User user, TimeQuery timeQuery, Integer pageNum, Integer size) {
		Page<User> page = new Page<>(pageNum, size);
		return this.baseMapper.pageAllUser(page, user, timeQuery);
	}


}
