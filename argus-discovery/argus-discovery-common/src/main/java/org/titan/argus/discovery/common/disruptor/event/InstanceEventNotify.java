package org.titan.argus.discovery.common.disruptor.event;

/**
 * @author starboyate
 */
public interface InstanceEventNotify {
	void register();

	void statusChange();
}
