package org.titan.argus.auth.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author starboyate
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
@ApiModel(description = "修改用户信息request")
public class UpdateUserRequest {
	@ApiModelProperty(value="id", name="id")
	private Long id;

	@ApiModelProperty(value="用户名", name="username")
	private String username;

	@ApiModelProperty(value="邮箱", name="email")
	private String email;

	@ApiModelProperty(value="角色Id集合", name="roles")
	private List<Long> roles = new ArrayList<>();

	@ApiModelProperty(value="部门Id集合", name="depts")
	private List<Long> depts = new ArrayList<>();

	@ApiModelProperty(value="状态", name="status")
	private Integer status;
}
