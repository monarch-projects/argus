package org.titan.argus.server.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.titan.argus.model.entities.Alarm;
import org.titan.argus.service.AlarmService;
import org.titan.argus.tools.alarm.core.AlarmHolder;

import java.util.List;

/**
 * @author starboyate
 */
@Component
public class AlarmInitializer implements ArgusInitializer, ApplicationListener<ApplicationReadyEvent> {
	@Autowired
	private AlarmService alarmService;

	@Override
	public void init() {
		AlarmHolder.addAll(alarmService.list());

	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {

	}
}
