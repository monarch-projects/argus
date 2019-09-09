package org.titan.argus.server.initializer;


import org.titan.argus.discovery.common.entities.ArgusInstance;

/**
 * @author starboyate
 */
public interface ArgusInitializer {
	void init(ArgusInstance instance);
}
