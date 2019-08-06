package org.titan.argus.discovery.common.entities;

import lombok.Builder;
import lombok.Data;

/**
 * @author starboyate
 *
 */
@Data
@Builder
public class ArgusServiceApp {
	private String appName;

	private Integer size;
}
