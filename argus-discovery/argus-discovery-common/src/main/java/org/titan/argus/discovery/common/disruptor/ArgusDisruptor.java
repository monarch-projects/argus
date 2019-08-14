package org.titan.argus.discovery.common.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.titan.argus.discovery.common.disruptor.event.InstanceEventFactory;
import org.titan.argus.discovery.common.disruptor.handler.InstanceEventHandler;
import org.titan.argus.discovery.common.disruptor.handler.InstanceExceptionHandler;
import org.titan.argus.discovery.common.disruptor.message.DisruptorMessage;
import org.titan.argus.tools.alarm.config.AlarmHolder;

/**
 * @author starboyate
 */
public class ArgusDisruptor {

	private Disruptor<DisruptorMessage> disruptor;

	private final JavaMailSender sender;

	private final AlarmHolder alarmHolder;
	public ArgusDisruptor(JavaMailSender sender, AlarmHolder alarmHolder) {
		this.sender = sender;
		this.alarmHolder = alarmHolder;
	}

	public void init() {
		InstanceEventFactory factory = new InstanceEventFactory();
		this.disruptor = new Disruptor<>(factory, 1024, DaemonThreadFactory.INSTANCE);
		this.disruptor.setDefaultExceptionHandler(new InstanceExceptionHandler());
		this.disruptor.handleEventsWith(new InstanceEventHandler(sender, alarmHolder));
		this.disruptor.start();
	}

	public void destroy() {
		this.disruptor.shutdown();
	}

	public RingBuffer<DisruptorMessage> getRingBuffer() {
		return this.disruptor.getRingBuffer();
	}
}
