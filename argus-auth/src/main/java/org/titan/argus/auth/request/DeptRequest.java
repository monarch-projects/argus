package org.titan.argus.auth.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author starboyate
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "部门信息request")
public class DeptRequest {
	@ApiModelProperty(value="id", name="id")
	private Long id;

	@ApiModelProperty(value="父级id", name="parentId")
	private Long parentId;

	@ApiModelProperty(value="排序", name="order")
	private Integer order;

	@ApiModelProperty(value="部门名称", name="name", example="开发部门")
	private String name;
}
