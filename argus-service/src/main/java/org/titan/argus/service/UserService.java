package org.titan.argus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.titan.argus.model.entities.User;

/**
 * @author starboyate
 */
public interface UserService extends IService<User> {
	User findByName(String userName);

	void addUser(User user);

	void delUser(Long id);

	void delUsers(Long[] ids);

	void updateUser(User user);

	void register(String userName, String password);
}
