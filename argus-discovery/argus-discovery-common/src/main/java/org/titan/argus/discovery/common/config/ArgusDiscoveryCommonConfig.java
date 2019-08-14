package org.titan.argus.discovery.common.config;

import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.titan.argus.discovery.common.disruptor.ArgusDisruptor;
import org.titan.argus.discovery.common.disruptor.event.InstanceEvent;
import org.titan.argus.discovery.common.disruptor.event.InstanceEventFactory;
import org.titan.argus.discovery.common.disruptor.handler.InstanceEventHandler;
import org.titan.argus.discovery.common.disruptor.handler.InstanceExceptionHandler;
import org.titan.argus.discovery.common.disruptor.message.DisruptorMessage;
import org.titan.argus.discovery.common.disruptor.producer.InstanceEventProducer;

/**
 * @author starboyate
 */
@Configuration
public class ArgusDiscoveryCommonConfig {

	@Autowired
	private JavaMailSender sender;

	@Bean(initMethod = "init", destroyMethod = "destroy")
	public ArgusDisruptor disruptor() {
		return new ArgusDisruptor(sender);
	}

	@Bean
	public InstanceEventProducer producer() {
		return new InstanceEventProducer(disruptor());
	}
}
