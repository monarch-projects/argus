package org.titan.argus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.titan.argus.model.entities.Dept;
import org.titan.argus.service.DeptService;
import org.titan.argus.storage.mysql.DeptMapper;

import java.util.List;

/**
 * @author starboyate
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {
	@Autowired
	private DeptMapper deptMapper;
	@Override
	public List<Dept> findAllDepts() {
		return deptMapper.selectList(new QueryWrapper<>());
	}

	@Override
	public void addDept(Dept dept) {
		deptMapper.insert(dept);
	}

	@Override
	public void delDept(Long id) {
		deptMapper.delete(new QueryWrapper<Dept>().eq("id", id));
	}

	@Override
	public void delDepts(List<Long> ids) {
		deptMapper.deleteBatchIds(ids);
	}

	@Override
	public void updateDept(Dept dept) {
		deptMapper.updateById(dept);
	}
}
