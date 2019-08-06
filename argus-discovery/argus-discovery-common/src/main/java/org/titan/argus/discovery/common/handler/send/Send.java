package org.titan.argus.discovery.common.handler.send;

import org.titan.argus.discovery.common.entities.ArgusDiscoveryEventInfo;

/**
 * @author starboyate
 */
public interface Send {
	void send(ArgusDiscoveryEventInfo info);
}
