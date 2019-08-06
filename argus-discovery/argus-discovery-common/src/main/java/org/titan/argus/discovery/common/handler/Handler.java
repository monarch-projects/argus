package org.titan.argus.discovery.common.handler;

import org.titan.argus.discovery.common.entities.ArgusDiscoveryEventInfo;

/**
 * @author starboyate
 */
public interface Handler {

	ArgusDiscoveryEventInfo invoke(String id, String appName, String event, long time);
}
