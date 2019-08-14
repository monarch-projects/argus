package org.titan.argus.tools.alarm.config;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.titan.argus.tools.alarm.helper.AlarmHelper;

import java.util.HashSet;
import java.util.Set;

/**
 * @author starboyate
 */
@Component
@Getter
public class AlarmHolder {
	private Set<AlarmHelper> alarms = new HashSet<>();

	public void setAlarmHolder(AlarmHelper helper) {
		this.alarms.add(helper);
	}
}
