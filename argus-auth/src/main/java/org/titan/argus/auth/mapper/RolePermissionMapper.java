package org.titan.argus.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.titan.argus.auth.model.RolePermission;

/**
 * @author starboyate
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
}
