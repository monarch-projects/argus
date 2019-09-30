package org.titan.argus.model.entities;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.titan.argus.model.vo.DeptVO;

import java.io.Serializable;

/**
 * @author starboyate
 */
@Data
@TableName("dept")
public class Dept implements Serializable {
	@TableId
	private Long id;

	@TableField("parent_id")
	private Long parentId;

	@TableField("name")
	private String name;

	@TableField("order")
	private Integer order;

	@TableField("create_time")
	private Long createTime;

	@TableField("update_time")
	private Long updateTime;

	public DeptVO convertToDeptVO() {
		return DeptVO.builder().id(this.id).parentId(this.parentId).name(this.name).build();
	}
}
