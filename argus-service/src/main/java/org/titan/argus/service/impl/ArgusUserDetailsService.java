package org.titan.argus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.titan.argus.model.entities.Role;
import org.titan.argus.model.entities.User;
import org.titan.argus.storage.mysql.RoleMapper;
import org.titan.argus.storage.mysql.UserMapper;

import java.util.List;

/**
 * @author starboyate
 */
@Service
public class ArgusUserDetailsService implements UserDetailsService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Override

	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		//查数据库
		User user = userMapper.loadUserByUsername(userName);
		if (null != user) {
			List<Role> roles = roleMapper.getRolesByUserId( user.getId() );
			user.setAuthorities( roles );
		}

		return user;
	}
}
