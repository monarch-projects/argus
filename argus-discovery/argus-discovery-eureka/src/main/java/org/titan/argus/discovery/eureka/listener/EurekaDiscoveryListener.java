package org.titan.argus.discovery.eureka.listener;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

/**
 * @author starboyate
 */
public class EurekaDiscoveryListener {
	@EventListener
	public void onApplicationReady(ApplicationReadyEvent event) {

	}
}
