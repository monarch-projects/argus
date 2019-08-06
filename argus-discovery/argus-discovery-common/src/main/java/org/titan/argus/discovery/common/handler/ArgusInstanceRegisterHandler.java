package org.titan.argus.discovery.common.handler;


import org.titan.argus.discovery.common.entities.ArgusDiscoveryEventInfo;
import org.springframework.stereotype.Component;

/**
 * @author starboyate
 */
@Component
public class ArgusInstanceRegisterHandler implements Handler{

	@Override
	public ArgusDiscoveryEventInfo invoke(String id, String appName, String event, long time) {
		ArgusDiscoveryEventInfo info = ArgusDiscoveryEventInfo.builder()
				.event(event)
				.id(id)
				.time(time).build();
		doInvoke(info);
		return info;

	}


	private void doInvoke(ArgusDiscoveryEventInfo info) {
		// TODO do somethings
	}
}
