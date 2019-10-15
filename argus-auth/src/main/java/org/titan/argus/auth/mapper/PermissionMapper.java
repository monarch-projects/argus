package org.titan.argus.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.titan.argus.auth.model.Permission;

import java.util.List;

/**
 * @author starboyate
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
	@Select( "SELECT p.point FROM role r "
			+ "        LEFT JOIN user_role ur ON (r.id = ur.role_id)"
			+ "        LEFT JOIN user u ON (u.id = ur.user_id)"
			+ "        LEFT JOIN role_permission rp ON (rp.role_id = r.id)"
			+ "        LEFT JOIN permission p ON (p.id = rp.id)"
			+ "        WHERE u.user_name = #{username} "
			+ "        AND p.point is not null"
			+ "        AND p.point <> ''" )
	List<String> findUserPointByUserName(@Param("username") String username);

	@Select( "SELECT p.* FROM role r "
			+ "        LEFT JOIN user_role ur ON (r.id = ur.role_id)"
			+ "        LEFT JOIN user u ON (u.id = ur.user_id)"
			+ "        LEFT JOIN role_permission rp ON (rp.role_id = r.id)"
			+ "        LEFT JOIN permission p ON (p.id = rp.id)"
			+ "        WHERE u.user_name = #{username} "
			+ "        AND p.type = 0")
	List<Permission> findUserMenuByUserName(@Param("username") String username);
}
