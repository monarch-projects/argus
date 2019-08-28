package org.titan.argus.tools.alarm.handler;


import org.titan.argus.model.entities.Alarm;
import org.titan.argus.model.entities.AlarmLog;

/**
 * @author starboyate
 */
public interface Sender {
	void send(AlarmLog alarm);
}
