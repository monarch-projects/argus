package org.titan.argus.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.titan.argus.auth.mapper.UserDeptMapper;
import org.titan.argus.auth.model.UserDept;
import org.titan.argus.auth.service.UserDeptService;

import java.util.List;

/**
 * @author starboyate
 */
@Service
public class UserDeptServiceImpl extends ServiceImpl<UserDeptMapper, UserDept> implements UserDeptService {

	@Override
	@Transactional
	public void updateDeptsByUserId(Long userId, List<Long> deptIds) {
		deptIds.forEach(item -> {
			this.baseMapper.update(null, new UpdateWrapper<UserDept>().set("dept_id", item).eq("user_id", userId));
		});
	}

	@Override
	public void addUserDepts(List<UserDept> userDepts) {
		userDepts.forEach(item -> {
			this.baseMapper.insert(item);
		});
	}
}
