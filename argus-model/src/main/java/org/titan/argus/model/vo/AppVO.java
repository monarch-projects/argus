package org.titan.argus.model.vo;

/**
 * @author starboyate
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author starboyate
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "注册中心上面的应用对象")
public class AppVO {
	@ApiModelProperty(value="app名称", name="name", example="eureka-client")
	private String name;

	@ApiModelProperty(value = "app下实例的个数", name = "size")
	private Integer size;
}
