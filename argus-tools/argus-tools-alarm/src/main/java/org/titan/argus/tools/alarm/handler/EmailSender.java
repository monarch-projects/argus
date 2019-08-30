package org.titan.argus.tools.alarm.handler;


import com.alibaba.fastjson.JSON;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.titan.argus.model.entities.Alarm;
import org.titan.argus.model.entities.AlarmLog;

/**
 * @author starboyate
 */
public class EmailSender implements Sender {
	private JavaMailSender sender;

	public EmailSender(JavaMailSender sender) {
		this.sender = sender;
	}
	@Override
	public void send(AlarmLog alarm) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(alarm.getAccount());
		message.setTo(alarm.getTo());
		message.setSubject(alarm.getSubject());
		message.setText(alarm.getBody());
		sender.send(message);
	}
}
