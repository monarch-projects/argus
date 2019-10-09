package org.titan.argus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.titan.argus.model.entities.User;
import org.titan.argus.service.UserService;
import org.titan.argus.storage.mysql.UserMapper;

/**
 * @author starboyate
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
	@Autowired
	private UserMapper userMapper;
	@Override
	public User findByName(String userName) {
		return null;
	}

	@Override
	public void addUser(User user) {

	}

	@Override
	public void delUser(Long id) {

	}

	@Override
	public void delUsers(Long[] ids) {

	}

	@Override
	public void updateUser(User user) {

	}

	@Override
	public void register(String userName, String password) {

	}
}
