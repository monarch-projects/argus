package org.titan.argus.tools.alarm.core;


import org.titan.argus.model.entities.Alarm;

/**
 * @author starboyate
 */
public interface AlarmHandler {
	void handler(Alarm alarm);
}
