package org.titan.argus.discovery.common.handler;


import org.titan.argus.discovery.common.entities.ArgusDiscoveryEventInfo;
import org.titan.argus.discovery.common.handler.send.EmailSender;
import org.titan.argus.discovery.common.handler.send.SenderHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @author starboyate
 */
@Component
public class ArgusInstanceOfflineHandler implements Handler {
	@Autowired
	private JavaMailSender sender;

	@Override
	public ArgusDiscoveryEventInfo invoke(String id, String appName, String event, long time) {
		ArgusDiscoveryEventInfo info = ArgusDiscoveryEventInfo.builder()
				.event(event)
				.id(id)
				.time(time).build();
		doInvoke(info);
		return info;

	}

	private void doInvoke(ArgusDiscoveryEventInfo info) {
		SenderHolder holder = new SenderHolder(new EmailSender(sender));
		holder.send(info);
	}
}
