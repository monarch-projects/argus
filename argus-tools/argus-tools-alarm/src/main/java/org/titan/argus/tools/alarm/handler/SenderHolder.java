package org.titan.argus.tools.alarm.handler;


import org.titan.argus.model.entities.AlarmLog;

/**
 * @author starboyate
 */
public class SenderHolder {
	private Sender sender;

	public SenderHolder(Sender sender) {
		this.sender = sender;
	}

	public void send(AlarmLog info) {
		this.sender.send(info);
	}
}
