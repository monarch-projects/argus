package org.titan.argus.discovery.common.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

/**
 * @author starboyate
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class InstanceEvent extends ApplicationEvent {
	private String id;

	private String appName;

	private String eventType;

	private long updateTime;
	public InstanceEvent(Object source, String id, String appName, String eventType, long updateTime) {
		super(source);
		this.id = id;
		this.appName = appName;
		this.eventType = eventType;
		this.updateTime = updateTime;
	}

}
