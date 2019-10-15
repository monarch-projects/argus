package org.titan.argus.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.titan.argus.auth.model.TimeQuery;
import org.titan.argus.auth.model.User;
import org.titan.argus.auth.request.AddUserRequest;
import org.titan.argus.auth.request.UpdateUserRequest;
import org.titan.argus.auth.vo.UserVO;

import java.util.List;


/**
 * @author starboyate
 */
public interface UserService extends IService<User> {

	User findByName(String userName);

	void addUser(AddUserRequest request);

	void delUser(Long id);

	void delUsers(List<Long> ids);

	void updateUser(UpdateUserRequest updateUserRequest);

	boolean checkUserName(String username);

	IPage<User> pageAllUser(User user, TimeQuery timeQuery, Integer pageNum, Integer size);

}
