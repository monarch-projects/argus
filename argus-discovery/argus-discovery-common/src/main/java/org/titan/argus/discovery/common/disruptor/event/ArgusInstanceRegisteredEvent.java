package org.titan.argus.discovery.common.disruptor.event;


/**
 * @author starboyate
 */
public class ArgusInstanceRegisteredEvent extends InstanceEvent {
	public ArgusInstanceRegisteredEvent(Object source, String id, String appName, String eventType, String updateTime) {
		super(source, id, appName, eventType, updateTime);
	}
}
