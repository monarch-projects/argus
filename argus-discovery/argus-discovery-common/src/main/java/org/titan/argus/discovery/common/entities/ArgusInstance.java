package org.titan.argus.discovery.common.entities;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * @author starboyate
 */
@Data
@Builder
public class ArgusInstance {
	private String id;

	private String appName;

	private String host;

	private Integer port;

	private String status;

	private Map<String, String> eventMap;
}
