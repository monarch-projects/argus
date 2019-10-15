package org.titan.argus.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.titan.argus.auth.model.Role;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
	@Select( "SELECT r.* FROM role r LEFT JOIN user_role ur ON r.id=ur.role_id LEFT JOIN user u ON u.id=ur.user_id WHERE u.user_name=#{username}" )
	List<Role> getRolesByUserName(@Param("username") String username);
}
