package org.titan.argus.discovery.common.disruptor.producer;

import com.lmax.disruptor.RingBuffer;
import org.titan.argus.discovery.common.disruptor.ArgusDisruptor;
import org.titan.argus.discovery.common.disruptor.event.InstanceEvent;
import org.titan.argus.discovery.common.disruptor.message.DisruptorMessage;

/**
 * @author starboyate
 * Currently used is a single producer model,
 * in the case of high concurrency, multi-threading, pushlish will have problems, should use pushlishEvent,
 * and the corresponding parameters of the disruptor configuration should also be modified
 */
public class InstanceEventProducer {
	private final ArgusDisruptor disruptor;

	public InstanceEventProducer(ArgusDisruptor disruptor) {
		this.disruptor = disruptor;
	}

	public void publish(InstanceEvent event) {
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
