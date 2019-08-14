package org.titan.argus.tools.alarm.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.titan.argus.tools.alarm.entities.Alarm;

/**
 * @author starboyate
 */
@Getter
public class AlarmRuleUpdateEvent extends ApplicationEvent {
	private Alarm alarm;

	public AlarmRuleUpdateEvent(Object source, Alarm alarm) {
		super(source);
		this.alarm = alarm;
	}
}
