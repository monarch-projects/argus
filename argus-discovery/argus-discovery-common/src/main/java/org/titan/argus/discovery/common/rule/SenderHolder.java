package org.titan.argus.discovery.common.rule;

import org.titan.argus.discovery.common.entities.ArgusDiscoveryEventInfo;

/**
 * @author starboyate
 */
public class SenderHolder {
	private Sender sender;

	public SenderHolder(Sender sender) {
		this.sender = sender;
	}

	public void send(ArgusDiscoveryEventInfo info) {
		this.sender.send(info);
	}
}
