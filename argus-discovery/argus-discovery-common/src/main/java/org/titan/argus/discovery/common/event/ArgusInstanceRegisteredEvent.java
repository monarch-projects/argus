package org.titan.argus.discovery.common.event;


/**
 * @author starboyate
 */
public class ArgusInstanceRegisteredEvent extends InstanceEvent {
	public ArgusInstanceRegisteredEvent(Object source, String id, String appName, String eventType,
			long updateTime) {
		super(source, id, appName, eventType, updateTime);
	}
}
