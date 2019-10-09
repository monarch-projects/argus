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
@TableName("role")
public class Role implements Serializable {
	@TableId
	private Long id;

	@TableField("name")
	private String name;

	@TableField("description")
	private String description;

	@TableField("create_time")
	private Long createTime;

	@TableField("update_time")
	private Long updateTime;
}
