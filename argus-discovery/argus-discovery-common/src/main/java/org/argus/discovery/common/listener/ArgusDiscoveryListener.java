package org.argus.discovery.common.listener;
import org.argus.discovery.common.event.InstanceCanceledEvent;
import org.argus.discovery.common.event.InstanceRegisteredEvent;
import org.argus.discovery.common.handler.AbstractArgusDiscoveryEventHandler;
import org.argus.discovery.common.handler.ArgusInstanceRegisterHandler;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author starboyate
 */
@Component
public class ArgusDiscoveryListener {


	@EventListener
	public void onInstanceRegister(InstanceRegisteredEvent event){
		AbstractArgusDiscoveryEventHandler handler = new ArgusInstanceRegisterHandler();
	}

	@EventListener
	public void onInstanceOffline(InstanceCanceledEvent event) {

	}

}
