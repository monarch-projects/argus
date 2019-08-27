package org.titan.argus.storage.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.titan.argus.model.entities.User;

/**
 * @author starboyate
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
	@Select( "select id , username , password from user where username = #{username}" )
	User loadUserByUsername(@Param("username") String username);
}
