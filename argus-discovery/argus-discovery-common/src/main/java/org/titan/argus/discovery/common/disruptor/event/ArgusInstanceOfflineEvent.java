package org.titan.argus.discovery.common.disruptor.event;

/**
 * @author starboyate
 */
public class ArgusInstanceOfflineEvent extends InstanceEvent {
	public ArgusInstanceOfflineEvent(Object source, String id, String appName, String eventType, String createTime) {
		super(source, id, appName, eventType, createTime);
	}
}
