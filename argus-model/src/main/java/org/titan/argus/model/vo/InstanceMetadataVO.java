package org.titan.argus.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author starboyate
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "实例元数据信息")
public class InstanceMetadataVO {
	@ApiModelProperty(value = "实例Id", name = "id", example = "localhost:argus-server:7878")
	private String id;

	@ApiModelProperty(value = "实例所属app name", name = "appName", example = "argus-server")
	private String appName;

	@ApiModelProperty(value = "实例ip", name = "ip", example = "localhost:7878")
	private String ip;

	@ApiModelProperty(value = "实例端口", name = "port", example = "7878")
	private Integer port;

	@ApiModelProperty(value = "实例状态", name = "status", example = "UP")
	private String status;

	@ApiModelProperty(value = "实例服务状态更改事件", name = "eventMap")
	private Map<String, String> eventMap;

	@ApiModelProperty(value = "当前实例是否是网关", name = "isGateway", example = "false")
	private Boolean isGateway;

	@ApiModelProperty(value = "当前服务实例的groupId", name = "groupId", example = "org.titan")
	private String groupId;

	@ApiModelProperty(value = "当前服务实例的artifactId", name = "artifactId", example = "argus-server")
	private String artifactId;

	@ApiModelProperty(value = "当前服务实例的version", name = "version", example = "1.0.0.RELEASE")
	private String version;

	@ApiModelProperty(value = "当前服务实例是否使用了redis", name = "isUsedRedis", example = "false")
	private Boolean isUsedRedis;

	@ApiModelProperty(value = "当前服务实例是否使用了mongodb", name = "isUsedMongodb", example = "false")
	private Boolean isUsedMongodb;

}
