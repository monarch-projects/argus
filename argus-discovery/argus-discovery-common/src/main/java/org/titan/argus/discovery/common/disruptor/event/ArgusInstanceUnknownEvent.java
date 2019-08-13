package org.titan.argus.discovery.common.disruptor.event;

/**
 * @author starboyate
 */
public class ArgusInstanceUnknownEvent extends InstanceEvent{
	public ArgusInstanceUnknownEvent(String id, String appName, String eventType, long updateTime) {
		super(id, appName, eventType, updateTime);
	}
}
