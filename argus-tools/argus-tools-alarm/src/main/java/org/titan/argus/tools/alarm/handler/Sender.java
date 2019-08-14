package org.titan.argus.tools.alarm.handler;


import org.titan.argus.tools.alarm.entities.Alarm;

/**
 * @author starboyate
 */
public interface Sender {
	void send(Alarm alarm);
}
