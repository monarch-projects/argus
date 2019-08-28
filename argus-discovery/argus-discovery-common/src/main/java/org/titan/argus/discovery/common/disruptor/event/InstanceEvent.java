package org.titan.argus.discovery.common.disruptor.event;


import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author starboyate
 */
@Getter
public class InstanceEvent extends ApplicationEvent {
	private String id;

	private String appName;

	private String eventType;

	private String updateTime;

	/**
	 * Create a new ApplicationEvent.
	 * @param source the object on which the event initially occurred (never {@code null})
	 */
	public InstanceEvent(Object source,String id, String appName, String eventType, String updateTime) {
		super(source);
		this.id = id;
		this.appName = appName;
		this.eventType = eventType;
		this.updateTime = updateTime;
	}
}
