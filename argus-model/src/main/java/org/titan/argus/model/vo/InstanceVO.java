package org.titan.argus.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author starboyate
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "返回的服务实例粗略信息")
public class InstanceVO {
	@ApiModelProperty(value = "实例Id", name = "id", example = "localhost:argus-server:7878")
	private String id;

	@ApiModelProperty(value = "实例所属app name", name = "appName", example = "argus-server")
	private String appName;

	@ApiModelProperty(value = "实例状态", name = "status", example = "UP")
	private String status;

}
