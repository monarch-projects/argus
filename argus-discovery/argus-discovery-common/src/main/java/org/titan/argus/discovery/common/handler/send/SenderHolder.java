package org.titan.argus.discovery.common.handler.send;

import org.titan.argus.discovery.common.entities.ArgusDiscoveryEventInfo;

/**
 * @author starboyate
 */
public class SenderHolder {
	private Send send;

	public SenderHolder(Send send) {
		this.send = send;
	}

	public void send(ArgusDiscoveryEventInfo info) {
		this.send.send(info);
	}
}
