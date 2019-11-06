package org.titan.argus.auth.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author starboyate
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "查询用户request")
public class QueryUserRequest {
	@ApiModelProperty(value="用户名", name="username")
	private String username;

	private Integer status;

	@ApiModelProperty(value="角色名称集合", name="roles")
	private List<String> roles = new ArrayList<>();

	@ApiModelProperty(value="部门名称集合", name="depts")
	private List<String> depts = new ArrayList<>();

	@ApiModelProperty(value="用户最后登录时间的起始时间", name="startTime")
	private Date startTime;

	@ApiModelProperty(value="用户最后登录时间的结束时间", name="endTime")
	private Date endTime;

}
