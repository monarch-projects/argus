package org.titan.argus.auth.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author starboyate
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_dept")
@Builder
public class UserDept implements Serializable {
	@TableId
	private Long id;

	@TableField("user_id")
	private Long userId;

	@TableField("user_id")
	private Long deptId;

	@TableField("create_time")
	private Date createTime;

	@TableField("update_time")
	private Date updateTime;
}
