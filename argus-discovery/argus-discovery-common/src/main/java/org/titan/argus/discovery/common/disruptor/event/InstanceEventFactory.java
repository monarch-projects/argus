package org.titan.argus.discovery.common.disruptor.event;

import com.lmax.disruptor.EventFactory;

/**
 * @author starboyate
 */
public class InstanceEventFactory implements EventFactory<InstanceEvent> {
	@Override
	public InstanceEvent newInstance() {
		return new InstanceEvent();
	}
}
