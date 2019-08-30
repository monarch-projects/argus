package org.titan.argus.discovery.common.disruptor.event;

/**
 * @author starboyate
 */
public class ArgusInstanceUnknownEvent extends InstanceEvent{
	public ArgusInstanceUnknownEvent(Object source, String id, String appName, String eventType, String updateTime) {
		super(source, id, appName, eventType, updateTime);
	}
}
