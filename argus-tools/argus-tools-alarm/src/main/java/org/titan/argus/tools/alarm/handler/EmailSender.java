package org.titan.argus.tools.alarm.handler;


import com.alibaba.fastjson.JSON;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.titan.argus.tools.alarm.entities.Alarm;
import org.titan.argus.tools.alarm.entities.MailAlarm;

/**
 * @author starboyate
 */
public class EmailSender implements Sender {
	private JavaMailSender sender;

	public EmailSender(JavaMailSender sender) {
		this.sender = sender;
	}
	@Override
	public void send(Alarm alarm) {
		MailAlarm mailAlarm = (MailAlarm) alarm;
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(mailAlarm.getFrom());
		message.setTo(mailAlarm.getTo());
		message.setSubject(mailAlarm.getSubject());
		message.setText(JSON.toJSONString(mailAlarm.getBody()));
		sender.send(message);
	}
}
