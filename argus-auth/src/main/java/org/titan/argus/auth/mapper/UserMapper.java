package org.titan.argus.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.titan.argus.auth.model.TimeQuery;
import org.titan.argus.auth.model.User;

import java.util.List;

/**
 * @author starboyate
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
	@Select("<script> "
			+ "select u.id as id, u.user_name as username,u.email as email,u.status as status,u.last_login_time as lastLoginTime from user u "
			+ "left join user_dept ud on u.id = ud.user_id "
			+ "left join dept d on d.id = ud.dept_id "
			+ "left join user_role ur on u.id = ur.user_id "
			+ "left join role r on r.id = ur.role_id "
			+ "where 1=1 "
			+ "<if test='user.username != null and user.username != \"\" '>"
			+ "and u.user_name = #{user.username}"
			+ "</if> "
			+ "<if test='user.deptList != null and user.deptList.size &gt; 0'> "
			+ "and d.id in "
			+ "<foreach collection='user.deptList' item='dept' open='(' separator=',' close=')'> "
			+ "#{dept.id} "
			+ "</foreach> "
			+ "</if> "
			+ "<if test='user.status != null and user.status != \"\" '>"
			+ "and u.status = #{user.status} "
			+ "</if> "
			+ "<if test='timeQuery.startTime != null'> "
			+ "and u.last_login_time &gt; #{timeQuery.startTime} "
			+ "</if> "
			+ "<if test='timeQuery.endTime != null'> "
			+ "and u.last_login_time &lt; #{timeQuery.endTime} "
			+ "</if> "
			+ "<if test='user.roleList != null and user.roleList.size &gt; 0'> "
			+ "and r.id in "
			+ "<foreach collection='user.roleList' item='role' open='(' separator=',' close=')'> "
			+ "#{role.id} "
			+ "</foreach> "
			+ "</if> "
			+ "</script>")
	@Results(id = "userMap", value = {
			@Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "username", property = "username", jdbcType = JdbcType.VARCHAR),
			@Result(column = "email", property = "email", jdbcType = JdbcType.VARCHAR),
			@Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
			@Result(column = "lastLoginTime", property = "lastLoginTime", jdbcType = JdbcType.DATE),
			@Result(property = "roleList", javaType = List.class, column = "username", many = @Many(select = "org.titan.argus.auth.mapper.RoleMapper.getRolesByUserName")),
			@Result(property = "deptList", javaType = List.class, column = "username", many = @Many(select = "org.titan.argus.auth.mapper.DeptMapper.findDeptListByUsername"))
	})
	IPage<User> pageAllUser(Page page, @Param("user") User user, @Param("timeQuery") TimeQuery timeQuery);



}
