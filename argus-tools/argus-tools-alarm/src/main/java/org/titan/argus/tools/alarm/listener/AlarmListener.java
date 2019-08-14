package org.titan.argus.tools.alarm.listener;

import org.springframework.context.event.EventListener;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.titan.argus.tools.alarm.config.AlarmHolder;
import org.titan.argus.tools.alarm.entities.MailAlarm;
import org.titan.argus.tools.alarm.event.AlarmRuleUpdateEvent;
import org.titan.argus.tools.alarm.helper.AlarmHelper;
import org.titan.argus.tools.alarm.helper.SenderHelper;

/**
 * @author starboyate
 */
public class AlarmListener {


	private AlarmHolder holder;

	public AlarmListener(AlarmHolder holder) {
		this.holder = holder;
	}

	@EventListener
	public void onAlarmRuleChange(AlarmRuleUpdateEvent event) {
		switch (event.getAlarm().getAlarmType().toLowerCase()) {
			case "email":
				emailOperating((MailAlarm)event.getAlarm());
				break;
			case "sms":
				break;
			default:
		}
	}

	private void emailOperating(MailAlarm alarm) {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		setEmailProperty(sender, alarm);
		SenderHelper senderHelper = SenderHelper.builder().mail(sender).build();
		this.holder.setAlarmHolder(AlarmHelper.builder().senderHelper(senderHelper).alarm(alarm).build());
	}

	private void setEmailProperty(JavaMailSenderImpl sender, MailAlarm alarm) {
		sender.setHost(alarm.getHost());
		sender.setPassword(alarm.getKey());
		sender.setUsername(alarm.getAccount());
	}
}
