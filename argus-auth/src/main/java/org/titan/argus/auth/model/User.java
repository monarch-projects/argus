package org.titan.argus.auth.model;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
	private String username;

	@TableField("password")
	private String password;

	@TableField("email")
	private String email;

	@TableField("status")
	private Integer status;

	@TableField(exist = false)
	private List<Dept> deptList;

	@TableField("create_time")
	private Date createTime;

	@TableField("update_time")
	private Date updateTime;

	@TableField("last_login_time")
	private Date lastLoginTime;

	@TableField(exist = false)
	private List<Role> roleList;
}
