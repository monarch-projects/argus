package org.titan.argus.auth.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author starboyate
 */
@Data
@Builder
@TableName("role_permission")
public class RolePermission implements Serializable {
	@TableId
	private Long id;

	@TableField("role_id")
	private Long roleId;

	@TableField(exist = false)
	private Role role;

	@TableId("permission_id")
	private Long permissionId;

	@TableField(exist = false)
	private Permission permission;
}
