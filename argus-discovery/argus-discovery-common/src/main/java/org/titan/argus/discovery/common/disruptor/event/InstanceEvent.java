package org.titan.argus.discovery.common.disruptor.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author starboyate
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstanceEvent {
	private String id;

	private String appName;

	private String eventType;

	private String updateTime;

}
