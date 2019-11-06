package org.titan.argus.auth.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.titan.argus.auth.model.Dept;
import org.titan.argus.auth.vo.DeptVO;
import org.titan.argus.auth.vo.Tree;

import java.util.List;

/**
 * @author starboyate
 */
public interface DeptService extends IService<Dept> {
	List<Dept> findAllDepts();

	void addDept(Dept dept);

	void delDept(Long id);

	void delDepts(List<Long> ids);

	void updateDept(Dept dept);

	List<? extends Tree> findDeptsTree();

	List<Dept> findDeptsByUserName(String username);
}
