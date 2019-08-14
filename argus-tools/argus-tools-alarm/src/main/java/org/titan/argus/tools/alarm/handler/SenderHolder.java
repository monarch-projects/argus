package org.titan.argus.tools.alarm.handler;


import org.titan.argus.tools.alarm.entities.Alarm;

/**
 * @author starboyate
 */
public class SenderHolder {
	private Sender sender;

	public SenderHolder(Sender sender) {
		this.sender = sender;
	}

	public void send(Alarm info) {
		this.sender.send(info);
	}
}
