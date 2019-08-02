package org.argus.discovery.common.entities;

import lombok.Builder;
import lombok.Data;

/**
 * @author starboyate
 */
@Data
@Builder
public class ArgusInstance {
	private String instanceId;

	private String appName;

	private String appGroup;

	private String host;

	private Integer port;

	private String status;
}
