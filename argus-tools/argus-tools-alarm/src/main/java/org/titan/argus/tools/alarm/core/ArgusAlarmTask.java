package org.titan.argus.tools.alarm.core;

import org.titan.argus.model.entities.Alarm;

/**
 * @author starboyate
 */
public class ArgusAlarmTask implements Runnable {
	private Alarm alarm;

	public ArgusAlarmTask(Alarm alarm) {
		this.alarm = alarm;
	}

	@Override
	public void run() {
		String eventType = this.alarm.getEventType();
		switch (eventType) {
			case AlarmEventTypeConstant.REGISTER:
			case AlarmEventTypeConstant.OFFLINE:
			case AlarmEventTypeConstant.UNKONOWN:
				break;
			case AlarmEventTypeConstant.REDIS:
				break;
			case AlarmEventTypeConstant.MONGODB:
				break;
		}
	}

}
