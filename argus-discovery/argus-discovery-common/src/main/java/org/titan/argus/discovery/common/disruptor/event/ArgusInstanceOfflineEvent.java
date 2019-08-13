package org.titan.argus.discovery.common.disruptor.event;

/**
 * @author starboyate
 */
public class ArgusInstanceOfflineEvent extends InstanceEvent {
	public ArgusInstanceOfflineEvent(String id, String appName, String eventType, long createTime) {
		super(id, appName, eventType, createTime);
	}
}
