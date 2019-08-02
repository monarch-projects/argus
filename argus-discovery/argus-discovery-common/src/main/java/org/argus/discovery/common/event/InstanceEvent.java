package org.argus.discovery.common.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.argus.discovery.common.entities.ArgusInstance;
import org.springframework.context.ApplicationEvent;

/**
 * @author starboyate
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class InstanceEvent extends ApplicationEvent {
	private ArgusInstance instance;

	private String eventType;

	private long createTime;
	public InstanceEvent(Object source, ArgusInstance instance, String eventType, long createTime) {
		super(source);
		this.instance = instance;
		this.eventType = eventType;
		this.createTime = createTime;
	}

}
