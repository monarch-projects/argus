package org.titan.argus.auth.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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

	@TableField("`name`")
	private String name;

	@TableField("`type`")
	private Integer type;

	@TableField("`order`")
	private Integer order;

	@TableField("extra")
	private String extra;

	@TableField("create_time")
	private Date createTime;

	@TableField("update_time")
	private Date updateTime;

	@TableField("point")
	private String point;
}
