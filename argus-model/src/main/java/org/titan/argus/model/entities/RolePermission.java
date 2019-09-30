package org.titan.argus.model.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author starboyate
 */
@Data
@TableName("role_permission")
public class RolePermission implements Serializable {
	@TableId
	private Long id;

	@TableField("role_id")
	private Long roleId;

	private Role role;

	@TableId("permission_id")
	private Long permissionId;

	private Permission permission;
}
