package org.titan.argus.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author starboyate
 */
@Data
@ApiModel(description = "树结构")
public class Tree<T> {
	@ApiModelProperty(value="id", name="id")
	private Long id;

	@ApiModelProperty(value="父级id", name="parentId")
	private Long parentId;

	@ApiModelProperty(value="排序", name="order")
	private Integer order;

	@ApiModelProperty(value="子元素", name="children")
	private List<Tree<T>> children = new ArrayList<>();
}
