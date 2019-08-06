package org.titan.argus.discovery.common.listener;
import org.titan.argus.discovery.common.event.ArgusInstanceCanceledEvent;
import org.titan.argus.discovery.common.event.InstanceEventManager;
import org.titan.argus.discovery.common.event.ArgusInstanceRegisteredEvent;
import org.titan.argus.discovery.common.handler.ArgusInstanceOfflineHandler;
import org.titan.argus.discovery.common.handler.ArgusInstanceRegisterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.cloud.client.discovery.event.ParentHeartbeatEvent;
import org.springframework.context.event.EventListener;

/**
 * @author starboyate
 */
public class ArgusDiscoveryListener {
	private Logger logger = LoggerFactory.getLogger(ArgusDiscoveryListener.class);
	private InstanceEventManager manager;

	public ArgusDiscoveryListener(InstanceEventManager manager) {
		this.manager = manager;
	}

	@Autowired
	private ArgusInstanceRegisterHandler registerHandler;

	@Autowired
	private ArgusInstanceOfflineHandler offlineHandler;

	@EventListener
	public void onApplicationReady(ApplicationReadyEvent event) {
		manager.init();
	}

	@EventListener
	public void onHeartbeat(HeartbeatEvent event) {
		manager.notifyEvent();
	}

	@EventListener
	public void onParentHeartbeat(ParentHeartbeatEvent event) {
		manager.notifyEvent();
	}

	@EventListener
	public void notifyOnArgusInstanceRegistered(ArgusInstanceRegisteredEvent event) {
		logger.info("notify registered, appName: {}, eventType: {}, updateTime: {}, instanceId: {}", event.getAppName(), event.getEventType(), event.getUpdateTime(), event.getId());
		registerHandler.invoke(event.getId(), event.getAppName(), event.getEventType(), event.getUpdateTime());
	}

	@EventListener
	public void notifyOnArgusInstanceOffline(ArgusInstanceCanceledEvent event) {
		logger.info("notify offline, appName: {}, eventType: {}, updateTime: {}, instanceId: {}", event.getAppName(), event.getEventType(), event.getUpdateTime(), event.getId());
		offlineHandler.invoke(event.getId(), event.getAppName(), event.getEventType(), event.getUpdateTime());
	}

}
