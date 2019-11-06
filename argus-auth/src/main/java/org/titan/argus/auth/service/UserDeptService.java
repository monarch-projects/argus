package org.titan.argus.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.titan.argus.auth.model.UserDept;

import java.util.List;

/**
 * @author starboyate
 */
public interface UserDeptService extends IService<UserDept> {
	void updateDeptsByUserId(Long userId, List<Long> deptIds);

	void addUserDepts(List<UserDept> userDepts);
}
