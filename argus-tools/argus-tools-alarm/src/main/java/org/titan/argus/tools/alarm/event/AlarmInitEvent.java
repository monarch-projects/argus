package org.titan.argus.tools.alarm.event;

import lombok.Data;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.titan.argus.model.entities.Alarm;

import java.util.List;

/**
 * @author starboyate
 */
@Getter
public class AlarmInitEvent extends ApplicationEvent {
	private List<Alarm> alarms;

	public AlarmInitEvent(Object source, List<Alarm> alarms) {
		super(source);
		this.alarms = alarms;
	}
}
