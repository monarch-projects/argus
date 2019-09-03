package org.titan.argus.server.listener;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.titan.argus.discovery.common.event.AlarmLogEvent;
import org.titan.argus.discovery.common.event.InstanceUpdateEvent;
import org.titan.argus.server.core.InstanceMetadataHolder;
import org.titan.argus.server.initializer.ArgusInitializer;
import org.titan.argus.service.AlarmLogService;

import java.util.Collection;

/**
 * @author starboyate
 */
//@Component
public class ArgusServerListener implements ApplicationContextAware {
	private final AlarmLogService alarmLogService;
	private final InstanceMetadataHolder holder;
	private ApplicationContext context;
	public ArgusServerListener(AlarmLogService alarmLogService, InstanceMetadataHolder holder) {
		this.alarmLogService = alarmLogService;
		this.holder = holder;
	}

	@EventListener
	public void addAlarmLog(AlarmLogEvent event) {
		this.alarmLogService.save(event.getLog());
	}

	@EventListener
	public void reloadInstanceMetadata(InstanceUpdateEvent event) {
		Collection<ArgusInitializer> values = this.context.getBeansOfType(ArgusInitializer.class).values();
		this.context.getBeansOfType(ArgusInitializer.class).values().forEach(ArgusInitializer::init);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
}
