package org.titan.argus.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.titan.argus.auth.mapper.DeptMapper;
import org.titan.argus.auth.model.Dept;
import org.titan.argus.auth.service.DeptService;
import org.titan.argus.auth.util.TreeUtil;
import org.titan.argus.auth.vo.DeptVO;
import org.titan.argus.auth.vo.Tree;
import org.titan.argus.common.exception.BusinessException;

import java.util.List;
import java.util.stream.Collectors;

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
		try {
			deptMapper.insert(dept);
		} catch (Exception ex) {
			throw new BusinessException("添加部门异常: "+ ex.getMessage());
		}
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
		try {
			deptMapper.updateById(dept);
		} catch (Exception ex) {
			throw new BusinessException("部门修改异常");
		}
	}

	@Override
	public List<? extends Tree> findDeptsTree() {
		List<Dept> deptList = this.list(new QueryWrapper<Dept>().orderByAsc("`order`"));
		List<DeptVO> deptVOList = deptList.stream().map(dept -> {
			DeptVO vo = new DeptVO();
			vo.setId(dept.getId());
			vo.setParentId(dept.getParentId());
			vo.setName(dept.getName());
			vo.setOrder(dept.getOrder());
			return vo;
		}).collect(Collectors.toList());
		return TreeUtil.build(deptVOList);
	}
}
