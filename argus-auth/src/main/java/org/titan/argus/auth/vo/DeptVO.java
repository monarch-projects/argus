package org.titan.argus.auth.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * @author starboyate
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors
@ApiModel(description = "部门信息")
public class DeptVO extends Tree<DeptVO> {
	@ApiModelProperty(value="部门名称", name="name", example="开发部门")
	private String name;
}
