package org.argus.discovery.common.event;


import org.argus.discovery.common.entities.ArgusInstance;

/**
 * @author starboyate
 */
public class InstanceCanceledEvent extends InstanceEvent {

	public InstanceCanceledEvent(Object source, ArgusInstance instance, String eventType, long createTime) {
		super(source, instance, eventType, createTime);
	}
}
