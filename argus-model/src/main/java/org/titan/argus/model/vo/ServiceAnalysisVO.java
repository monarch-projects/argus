package org.titan.argus.model.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author starboyate
 */
public class ServiceAnalysisVO {
	@ApiModelProperty(value = "服务总数", name = "size", example = "10")
	private Integer size;

	@ApiModelProperty(value = "UP状态服务数比例", name = "upPercent", example = "90%")
	private Integer upPercent;

	@ApiModelProperty(value = "DOWN状态服务数比例", name = "downPercent", example = "10%")
	private Integer downPercent;

	@ApiModelProperty(value = "风险级别", name = "riskLevel", example = "低")
	private String riskLevel;
}
