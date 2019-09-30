package org.titan.argus.storage.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.titan.argus.model.entities.UserRole;

/**
 * @author starboyate
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
}
