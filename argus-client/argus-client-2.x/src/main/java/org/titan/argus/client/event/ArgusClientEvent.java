package org.titan.argus.client.event;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.titan.argus.client.boot.ArgusClient;

/**
 * @author starboyate
 */
public class ArgusClientEvent {

	@EventListener
	public void onApplicationReady(ApplicationReadyEvent event) {
		ArgusClient.initialize();
	}

	@EventListener
	public void onApplicationShutDown(ContextClosedEvent event) {
		ArgusClient.shutdown();
	}
}
