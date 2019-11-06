package org.titan.argus.auth.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author starboyate
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "角色信息request")
public class RoleRequest {
	@ApiModelProperty(value="id", name="id")
	private Long id;

	@ApiModelProperty(value="角色名称", name="name")
	private String name;

	@ApiModelProperty(value="角色描述", name="description")
	private String description;

	@ApiModelProperty(value="角色包含的权限（ID）集合", name="permissions")
	private List<Long> permissions;
}
