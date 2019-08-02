package org.argus.discovery.common.handler;


import org.argus.discovery.common.entities.ArgusDiscoveryEventInfo;

import java.util.Date;

/**
 * @author starboyate
 */
public abstract class AbstractArgusDiscoveryEventHandler implements Handler {

	@Override
	public ArgusDiscoveryEventInfo invoke(Integer instanceId, String event) {
		ArgusDiscoveryEventInfo info = ArgusDiscoveryEventInfo.builder()
				.event(event)
				.instanceId(instanceId)
				.time(new Date()).build();
		doInvoke();
		return info;

	}

	protected abstract void doInvoke();
}
