package org.titan.argus.server.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.titan.argus.discovery.common.event.AlarmLogEvent;
import org.titan.argus.model.entities.Alarm;
import org.titan.argus.network.httpclient.util.ArgusHttpClient;
import org.titan.argus.server.core.ArgusActuatorConstant;
import org.titan.argus.service.AlarmLogService;
import org.titan.argus.service.AlarmService;
import org.titan.argus.service.exception.BusinessException;
import org.titan.argus.tools.alarm.event.AlarmInitEvent;
import org.titan.argus.tools.alarm.event.MiddleWareNodeEvent;

import java.util.List;

/**
 * @author starboyate
 */
@Component
public class ArgusServerListener {
	@Autowired
	private AlarmLogService alarmLogService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private AlarmService alarmService;

	@EventListener
	public void addAlarmLog(AlarmLogEvent event) {
		this.alarmLogService.save(event.getLog());
	}

	@EventListener
	public void initAlarm(ApplicationReadyEvent event) {
		List<Alarm> list = alarmService.list();
		this.publisher.publishEvent(new AlarmInitEvent(this, list));
	}

}
