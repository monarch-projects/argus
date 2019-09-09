package org.titan.argus.discovery.common.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.titan.argus.discovery.common.entities.ArgusInstance;

/**
 * @author starboyate
 */
@Getter
public class InstanceUpdateEvent extends ApplicationEvent {
	private ArgusInstance instance;
	/**
	 * Create a new ApplicationEvent.
	 * @param source the object on which the event initially occurred (never {@code null})
	 */
	public InstanceUpdateEvent(Object source, ArgusInstance instance) {
		super(source);
		this.instance = instance;
	}
}
