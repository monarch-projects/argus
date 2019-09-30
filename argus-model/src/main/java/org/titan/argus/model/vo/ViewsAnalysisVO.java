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
public class ViewsAnalysisVO {
	@ApiModelProperty(value = "总访问量", name = "visits", example = "100000")
	private Long visits;
}
