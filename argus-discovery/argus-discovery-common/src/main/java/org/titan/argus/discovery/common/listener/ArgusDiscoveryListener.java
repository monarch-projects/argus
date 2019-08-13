package org.titan.argus.discovery.common.listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.cloud.client.discovery.event.ParentHeartbeatEvent;
import org.springframework.context.event.EventListener;
import org.titan.argus.discovery.common.disruptor.event.InstanceEventNotify;

/**
 * @author starboyate
 */
public class ArgusDiscoveryListener {
	private Logger logger = LoggerFactory.getLogger(ArgusDiscoveryListener.class);
	private InstanceEventNotify eventNotify;

	public ArgusDiscoveryListener(InstanceEventNotify eventNotify) {
		this.eventNotify = eventNotify;
	}


	@EventListener
	public void onApplicationReady(ApplicationReadyEvent event) {
		eventNotify.register();
	}

	@EventListener
	public void onHeartbeat(HeartbeatEvent event) {
		eventNotify.statusChange();
	}

	@EventListener
	public void onParentHeartbeat(ParentHeartbeatEvent event) {
		eventNotify.statusChange();
	}

//	@EventListener
//	public void notifyOnArgusInstanceRegistered(ArgusInstanceRegisteredEvent event) {
//		logger.info("notify registered, appName: {}, eventType: {}, updateTime: {}, instanceId: {}", event.getAppName(), event.getEventType(), event.getUpdateTime(), event.getId());
//		registerHandler.invoke(event.getId(), event.getAppName(), event.getEventType(), event.getUpdateTime());
//	}
//
//	@EventListener
//	public void notifyOnArgusInstanceOffline(ArgusInstanceCanceledEvent event) {
//		logger.info("notify offline, appName: {}, eventType: {}, updateTime: {}, instanceId: {}", event.getAppName(), event.getEventType(), event.getUpdateTime(), event.getId());
//		offlineHandler.invoke(event.getId(), event.getAppName(), event.getEventType(), event.getUpdateTime());
//	}

}
