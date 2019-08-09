package org.titan.argus.discovery.common.entities;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author starboyate
 */
@Builder
@Data
public class ArgusDiscoveryEventInfo {
	private long time;

	private String event;

	private String id;

	private String appName;
}