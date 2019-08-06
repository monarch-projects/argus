package org.titan.argus.discovery.common.handler.send;

import com.alibaba.fastjson.JSON;
import org.titan.argus.discovery.common.entities.ArgusDiscoveryEventInfo;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * @author starboyate
 */
public class EmailSender implements Send {
	private JavaMailSender sender;

	public EmailSender(JavaMailSender sender) {
		this.sender = sender;
	}
	@Override
	public void send(ArgusDiscoveryEventInfo info) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("3135205134@qq.com");
		message.setTo("2925776766@qq.com");
		message.setSubject("主题：服务下线");
		message.setText(JSON.toJSONString(info));
		sender.send(message);
	}
}
