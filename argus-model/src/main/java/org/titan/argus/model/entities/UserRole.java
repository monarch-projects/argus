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
@TableName("user_role")
public class UserRole implements Serializable {
	@TableId
	private Long id;

	@TableField("user_id")
	private Long userId;

	private User user;

	@TableField("role_id")
	private Long roleId;

	private Role role;
}
