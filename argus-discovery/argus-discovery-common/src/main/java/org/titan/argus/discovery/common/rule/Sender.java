package org.titan.argus.discovery.common.rule;

import org.titan.argus.discovery.common.entities.ArgusDiscoveryEventInfo;

/**
 * @author starboyate
 */
public interface Sender {
	void send(ArgusDiscoveryEventInfo info);
}
