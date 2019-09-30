package org.titan.argus.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author starboyate
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class InstanceAnalysisVO {
	@ApiModelProperty(value = "实例总数", name = "size", example = "20")
	private Integer size;

	@ApiModelProperty(value = "可用率", name = "availability", example = "99%")
	private Integer availability;
}
