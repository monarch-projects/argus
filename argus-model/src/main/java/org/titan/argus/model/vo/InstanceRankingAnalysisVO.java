package org.titan.argus.model.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author starboyate
 */
public class InstanceRankingAnalysisVO {
	@ApiModelProperty(value = "实例Id", name = "id", example = "localhost:argus-server:7878")
	private String id;

	@ApiModelProperty(value = "实例所属app name", name = "appName", example = "argus-server")
	private String appName;

	@ApiModelProperty(value = "流量", name = "visits", example = "10000")
	private Long visits;
}
