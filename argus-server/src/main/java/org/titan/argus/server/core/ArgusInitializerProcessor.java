package org.titan.argus.server.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.titan.argus.discovery.common.entities.ArgusInstance;
import org.titan.argus.discovery.common.event.InstanceUpdateEvent;
import org.titan.argus.server.initializer.ArgusInitializer;

import java.util.Collection;

/**
 * @author starboyate
 */
@Component
public class ArgusInitializerProcessor implements ApplicationListener<InstanceUpdateEvent>, ApplicationContextAware {
	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}


	private void init(ArgusInstance instance) {
		Collection<ArgusInitializer> values = this.applicationContext.getBeansOfType(ArgusInitializer.class).values();
		this.applicationContext.getBeansOfType(ArgusInitializer.class).values().forEach(item -> item.init(instance));
	}

	@Override
	public void onApplicationEvent(InstanceUpdateEvent event) {
		init(event.getInstance());
	}
}
