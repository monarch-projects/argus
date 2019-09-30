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
@TableName("permission")
public class Permission implements Serializable {
	@TableId
	private Long id;

	@TableField("parent_id")
	private Long parentId;

	@TableField("url")
	private String url;

	@TableField("keyword")
	private String keyword;

	@TableField("type")
	private Integer type;

	@TableField("order")
	private Integer order;

	@TableField("extra")
	private String extra;

	@TableField("create_time")
	private Long createTime;

	@TableField("update_time")
	private Long updateTime;
}
