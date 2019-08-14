package org.titan.argus.discovery.common.disruptor.event;

import com.lmax.disruptor.EventFactory;
import org.titan.argus.discovery.common.disruptor.message.DisruptorMessage;

/**
 * @author starboyate
 */
public class InstanceEventFactory implements EventFactory<DisruptorMessage> {
	@Override
	public DisruptorMessage newInstance() {
		return new DisruptorMessage();
	}
}
