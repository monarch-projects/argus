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
@TableName("user_role")
@Builder
public class UserRole implements Serializable {
	@TableId
	private Long id;

	@TableField("user_id")
	private Long userId;

	@TableField(exist = false)
	private User user;

	@TableField("role_id")
	private Long roleId;

	@TableField(exist = false)
	private Role role;
}
