package org.titan.argus.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.titan.argus.model.entities.Dept;

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
}
