package org.titan.argus.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.titan.argus.auth.model.Dept;

import java.util.List;

/**
 * @author starboyate
 */
@Mapper
public interface DeptMapper extends BaseMapper<Dept> {
	@Select("SELECT d.* FROM dept d LEFT JOIN user_dept ud ON d.id=ud.dept_id LEFT JOIN user u ON u.id=ud.user_id WHERE u.user_name=#{username}")
	List<Dept> findDeptListByUsername(@Param("username")String username);
}
