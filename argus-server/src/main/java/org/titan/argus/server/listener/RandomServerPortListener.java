package org.titan.argus.server.listener;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.titan.argus.server.config.RandomServerPortPropertySource;

/**
 * @author starboyate
 * 随机端口监听类
 */
public class RandomServerPortListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent applicationEnvironmentPreparedEvent) {
        applicationEnvironmentPreparedEvent.getEnvironment().getPropertySources().addLast(new RandomServerPortPropertySource());
    }

}
