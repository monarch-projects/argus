package org.titan.argus.discovery.eureka.entities;

import lombok.Builder;
import lombok.Data;

/**
 * @author starboyate
 *
 */
@Data
@Builder
public class ArgusEurekaServiceApp {
	private String appName;

	private Integer size;
}
