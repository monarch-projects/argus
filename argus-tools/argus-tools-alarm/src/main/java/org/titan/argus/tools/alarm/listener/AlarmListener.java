package org.titan.argus.tools.alarm.listener;
import org.springframework.context.event.EventListener;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.titan.argus.model.entities.Alarm;
import org.titan.argus.tools.alarm.config.AlarmHolder;
import org.titan.argus.tools.alarm.event.AlarmInitEvent;
import org.titan.argus.tools.alarm.event.AlarmRuleUpdateEvent;
import org.titan.argus.tools.alarm.event.MiddleWareNodeEvent;
import org.titan.argus.tools.alarm.helper.AlarmHelper;
import org.titan.argus.tools.alarm.helper.SenderHelper;

import java.util.List;

/**
 * @author starboyate
 */
public class AlarmListener {


	private AlarmHolder holder;



	public AlarmListener(AlarmHolder holder) {
		this.holder = holder;
	}

	@EventListener
	public void init(AlarmInitEvent event) {
		List<Alarm> alarmList = event.getAlarms();
		alarmList.forEach(this::changeAlarmRule);
	}


	@EventListener
	public void onAlarmRuleChange(AlarmRuleUpdateEvent event) {
		changeAlarmRule(event.getAlarm());
	}

	private void changeAlarmRule(Alarm alarm) {
		switch (alarm.getAlarmType().toLowerCase()) {
			case "email":
				emailOperating(alarm);
				break;
			case "sms":
				break;
			default:
		}
	}

	private void emailOperating(Alarm alarm) {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		setEmailProperty(sender, alarm);
		SenderHelper senderHelper = SenderHelper.builder().mail(sender).build();
		this.holder.setAlarmHolder(AlarmHelper.builder().senderHelper(senderHelper).alarm(alarm).build());
	}

	private void setEmailProperty(JavaMailSenderImpl sender, Alarm alarm) {
		sender.setHost(alarm.getHost());
		sender.setPassword(alarm.getKey());
		sender.setUsername(alarm.getAccount());
	}
}
