package org.titan.argus.discovery.common.disruptor.producer;

import com.lmax.disruptor.RingBuffer;
import org.titan.argus.discovery.common.disruptor.event.InstanceEvent;

/**
 * @author starboyate
 */
public class InstanceEventProducer {
	private final RingBuffer<InstanceEvent> ringBuffer;

	public InstanceEventProducer(RingBuffer<InstanceEvent> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}

	public void onData(InstanceEvent event) {
		long sequence  = ringBuffer.next();
		try {
			InstanceEvent instanceEvent = ringBuffer.get(sequence);
			instanceEvent = event;
		} finally {
			ringBuffer.publish(sequence);
		}
	}
}
