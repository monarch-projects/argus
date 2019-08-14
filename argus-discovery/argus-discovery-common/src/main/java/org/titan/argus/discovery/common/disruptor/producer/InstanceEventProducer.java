package org.titan.argus.discovery.common.disruptor.producer;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import org.titan.argus.discovery.common.disruptor.ArgusDisruptor;
import org.titan.argus.discovery.common.disruptor.event.InstanceEvent;
import org.titan.argus.discovery.common.disruptor.message.DisruptorMessage;

/**
 * @author starboyate
 */
public class InstanceEventProducer {
	private final ArgusDisruptor disruptor;

	public InstanceEventProducer(ArgusDisruptor disruptor) {
		this.disruptor = disruptor;
	}

	public void onData(InstanceEvent event) {
		RingBuffer<DisruptorMessage> ringBuffer = disruptor.getRingBuffer();
		long sequence  = ringBuffer.next();
		try {
			DisruptorMessage message = ringBuffer.get(sequence);
			message.setEvent(event);
		} finally {
			ringBuffer.publish(sequence);
		}
	}
}
