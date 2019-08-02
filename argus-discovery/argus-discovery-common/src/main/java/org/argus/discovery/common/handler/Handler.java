package org.argus.discovery.common.handler;

import org.argus.discovery.common.entities.ArgusDiscoveryEventInfo;

/**
 * @author starboyate
 */
public interface Handler {

	ArgusDiscoveryEventInfo invoke(Integer instanceId, String event);
}
