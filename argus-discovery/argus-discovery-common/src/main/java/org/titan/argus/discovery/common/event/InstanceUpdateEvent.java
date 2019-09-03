package org.titan.argus.discovery.common.event;

import org.springframework.context.ApplicationEvent;
import org.titan.argus.discovery.common.entities.ArgusInstance;

/**
 * @author starboyate
 */
public class InstanceUpdateEvent extends ApplicationEvent {
	/**
	 * Create a new ApplicationEvent.
	 * @param source the object on which the event initially occurred (never {@code null})
	 */
	public InstanceUpdateEvent(Object source) {
		super(source);
	}
}
