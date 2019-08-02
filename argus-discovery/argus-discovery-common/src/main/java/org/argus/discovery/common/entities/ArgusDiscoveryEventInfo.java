package org.argus.discovery.common.entities;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author starboyate
 */
@Builder
@Data
public class ArgusDiscoveryEventInfo {
	private Date time;

	private String event;

	private Integer instanceId;
}
