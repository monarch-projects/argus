package org.titan.argus.auth.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Date;

/**
 * @author starboyate
 */
@Data
@TableName("role")
@Builder
public class Role implements Serializable {
	@TableId
	private Long id;

	@TableField("name")
	private String name;

	@TableField("description")
	private String description;

	@TableField("create_time")
	private Date createTime;

	@TableField("update_time")
	private Date updateTime;

}
