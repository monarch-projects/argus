package org.titan.argus.server.listener;


import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.titan.argus.discovery.common.event.AlarmLogEvent;
import org.titan.argus.service.AlarmLogService;

/**
 * @author starboyate
 */
//@Component
public class ArgusServerListener {
	private final AlarmLogService alarmLogService;
	public ArgusServerListener(AlarmLogService alarmLogService) {
		this.alarmLogService = alarmLogService;
	}

	@EventListener
	public void addAlarmLog(AlarmLogEvent event) {
		this.alarmLogService.save(event.getLog());
	}


}
