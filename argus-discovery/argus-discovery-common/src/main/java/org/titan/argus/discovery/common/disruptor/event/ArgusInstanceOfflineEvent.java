package org.titan.argus.discovery.common.disruptor.event;

/**
 * @author starboyate
 */
public class ArgusInstanceOfflineEvent extends InstanceEvent {
	public ArgusInstanceOfflineEvent(String id, String appName, String eventType, String createTime) {
		super(id, appName, eventType, createTime);
	}
}
