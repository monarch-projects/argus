package org.titan.argus.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.titan.argus.tools.alarm.config.AlarmHolder;
import org.titan.argus.tools.alarm.entities.Alarm;
import org.titan.argus.tools.alarm.entities.MailAlarm;
import org.titan.argus.tools.alarm.event.AlarmRuleUpdateEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author starboyate
 */
@RestController
public class TestController {
	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private JavaMailSenderImpl sender;

	@Autowired
	private AlarmHolder holder;

	@PostMapping("/alarm")
	public void alarm(@RequestBody MailAlarm alarm) {
		this.publisher.publishEvent(new AlarmRuleUpdateEvent(this, alarm));
	}

	@GetMapping("/alarm")
	public Set getProperties() {
		return holder.getAlarms();
	}


}
