package org.titan.argus.auth.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author starboyate
 */
@Data
@TableName("dept")
@Builder
public class Dept implements Serializable {
	@TableId
	private Long id;

	@TableField("parent_id")
	private Long parentId;

	@TableField("`name`")
	private String name;

	@TableField("`order`")
	private Integer order;

	@TableField("create_time")
	private Date createTime;

	@TableField("update_time")
	private Date updateTime;

//	public DeptVO convertToDeptVO() {
//		return new DeptVO().setId(this.id).setParentId(this.parentId).setName(this.name);
//	}
}
