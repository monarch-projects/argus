package org.titan.argus.discovery.common.event;


/**
 * @author starboyate
 */
public class ArgusInstanceCanceledEvent extends InstanceEvent {

	public ArgusInstanceCanceledEvent(Object source, String id, String appName, String eventType,
			long createTime) {
		super(source, id, appName, eventType, createTime);
	}
}
