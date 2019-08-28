package org.titan.argus.tools.alarm.core;

import org.titan.argus.model.entities.Alarm;

/**
 * @author starboyate
 */
public abstract class AbstractAlarmHandler implements AlarmHandler {
	private final Alarm alarm;

	public AbstractAlarmHandler(Alarm alarm) {
		this.alarm = alarm;
	}

	protected abstract void doHandler();

	@Override
	public void handler() {
		doHandler();
	}

}
