package org.titan.argus.discovery.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.titan.argus.discovery.common.disruptor.ArgusDisruptor;
import org.titan.argus.discovery.common.disruptor.producer.InstanceEventProducer;
import org.titan.argus.tools.alarm.config.AlarmHolder;

/**
 * @author starboyate
 */
@Configuration
public class ArgusDiscoveryCommonConfig {

	@Autowired
	private JavaMailSenderImpl sender;

	@Autowired
	private AlarmHolder holder;

	@Bean(initMethod = "init", destroyMethod = "destroy")
	public ArgusDisruptor disruptor() {
		return new ArgusDisruptor(sender, holder);
	}

	@Bean
	public InstanceEventProducer producer() {
		return new InstanceEventProducer(disruptor());
	}
}
