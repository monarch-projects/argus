package org.titan.argus.storage.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.titan.argus.model.entities.RolePermisson;

import java.util.List;

/**
 * @author starboyate
 */
@Mapper
public interface PermissionMapper extends BaseMapper<RolePermisson> {
	@Select("SELECT A.NAME AS roleName,C.url FROM role AS A LEFT JOIN role_permission B ON A.id=B.role_id LEFT JOIN permission AS C ON B.permission_id=C.id")
	List<RolePermisson> getRolePermissions();
}
