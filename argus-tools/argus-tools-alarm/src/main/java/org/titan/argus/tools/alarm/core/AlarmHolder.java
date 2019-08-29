package org.titan.argus.tools.alarm.core;

import com.google.common.collect.Sets;
import org.titan.argus.model.entities.Alarm;

import java.util.Collection;
import java.util.Set;

/**
 * @author starboyate
 */
public final class AlarmHolder {
	private static final Set<Alarm> ALARM_SET;

	static {
		ALARM_SET = Sets.newConcurrentHashSet();
	}

	public static void addAlarm(Alarm alarm) {
		ALARM_SET.add(alarm);
	}

	public static void addAllAlarm(Collection<Alarm> collection) {
		ALARM_SET.addAll(collection);
	}

	public static Set<Alarm> getAllAlarm() {
		return ALARM_SET;
	}
}
