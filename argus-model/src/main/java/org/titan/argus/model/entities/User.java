package org.titan.argus.model.entities;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author starboyate
 */
@Data
@TableName("user")
public class User implements Serializable {

	@TableId
	private Long id;

	@TableField("user_name")
	private String userName;

	@TableField("password")
	private String password;

	@TableField("email")
	private String email;

	@TableField("phone")
	private String phone;

	@TableField("status")
	private Integer status;

	@TableField("dept_id")
	private Long deptId;

	private String deptName;

	@TableField("create_time")
	private Long createTime;

	@TableField("update_time")
	private Long updateTime;

	@TableField("last_login_time")
	private Long lastLoginTime;
}
