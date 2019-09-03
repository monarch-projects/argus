package org.titan.argus.discovery.common.entities;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.Map;

/**
 * @author starboyate
 */
@Data
@Builder
@EqualsAndHashCode(exclude = {"eventMap"})
public class ArgusInstance {
	private String id;

	private String appName;

	private String host;

	private Integer port;

	private String status;

	private String homePageUrl;

	private Map<String, String> eventMap;


}
