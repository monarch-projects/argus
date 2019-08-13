package org.titan.argus.discovery.common.config;

import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.titan.argus.discovery.common.disruptor.event.InstanceEvent;
import org.titan.argus.discovery.common.disruptor.event.InstanceEventFactory;
import org.titan.argus.discovery.common.disruptor.handler.InstanceEventHandler;
import org.titan.argus.discovery.common.disruptor.handler.InstanceExceptionHandler;
import org.titan.argus.discovery.common.disruptor.producer.InstanceEventProducer;

/**
 * @author starboyate
 */
@Configuration
public class DisruptorConfig {

	@Autowired
	private JavaMailSender sender;

	@Bean
	public Disruptor<InstanceEvent> disruptor() {
		InstanceEventFactory factory = new InstanceEventFactory();
		Disruptor<InstanceEvent> disruptor = new Disruptor<>(factory, 1024, DaemonThreadFactory.INSTANCE);
		disruptor.setDefaultExceptionHandler(new InstanceExceptionHandler());
		disruptor.handleEventsWith(new InstanceEventHandler(sender));
		disruptor.start();
		return disruptor;
	}

	@Bean
	public InstanceEventProducer producer() {
		return new InstanceEventProducer(disruptor().getRingBuffer());
	}
}
