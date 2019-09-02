package org.titan.argus.server.core;

import org.springframework.beans.BeansException;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.titan.argus.server.initializer.ArgusInitializer;

import java.util.Collection;

/**
 * @author starboyate
 */
@Component
public class ArgusInitializerProcessor implements ApplicationListener<ApplicationStartedEvent>, ApplicationContextAware {
	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {
		init();
	}

	private void init() {
		Collection<ArgusInitializer> values = this.applicationContext.getBeansOfType(ArgusInitializer.class).values();
		this.applicationContext.getBeansOfType(ArgusInitializer.class).values().forEach(ArgusInitializer::init);
	}
}
