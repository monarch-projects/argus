package org.titan.argus.discovery.common.disruptor.event;


/**
 * @author starboyate
 */
public class ArgusInstanceRegisteredEvent extends InstanceEvent {
	public ArgusInstanceRegisteredEvent(String id, String appName, String eventType, String updateTime) {
		super(id, appName, eventType, updateTime);
	}
}
