package org.titan.argus.storage.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.titan.argus.model.entities.Permission;
import org.titan.argus.model.entities.RolePermission;

import java.util.List;

/**
 * @author starboyate
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

}
