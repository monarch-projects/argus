package org.titan.argus.discovery.common.disruptor.handler;

import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.titan.argus.discovery.common.disruptor.event.ArgusInstanceOfflineEvent;
import org.titan.argus.discovery.common.disruptor.event.ArgusInstanceRegisteredEvent;
import org.titan.argus.discovery.common.disruptor.event.InstanceEvent;
import org.titan.argus.discovery.common.disruptor.message.DisruptorMessage;
import org.titan.argus.discovery.common.entities.ArgusDiscoveryEventInfo;
import org.titan.argus.discovery.common.rule.EmailSender;
import org.titan.argus.discovery.common.rule.SenderHolder;

/**
 * @author starboyate
 */
public class InstanceEventHandler implements EventHandler<DisruptorMessage> {
	private Logger logger = LoggerFactory.getLogger(InstanceEventHandler.class);
	private final JavaMailSender sender;
	public InstanceEventHandler(JavaMailSender sender) {
		this.sender = sender;
	}
	@Override
	public void onEvent(DisruptorMessage message, long l, boolean b) throws Exception {
		InstanceEvent event = message.getEvent();
		ArgusDiscoveryEventInfo info = ArgusDiscoveryEventInfo.builder()
				.event(event.getEventType())
				.id(event.getId())
				.createTime(event.getUpdateTime())
				.build();
		if (event instanceof ArgusInstanceOfflineEvent) {
			logger.info("notify registered, appName: {}, eventType: {}, updateTime: {}, instanceId: {}", event.getAppName(), event.getEventType(), event.getUpdateTime(), event.getId());
			offline(info);
		} else if (event instanceof ArgusInstanceRegisteredEvent) {
			logger.info("notify offline, appName: {}, eventType: {}, updateTime: {}, instanceId: {}", event.getAppName(), event.getEventType(), event.getUpdateTime(), event.getId());
		}
	}

	private void offline(ArgusDiscoveryEventInfo info) {
		SenderHolder holder = new SenderHolder(new EmailSender(sender));
		holder.send(info);
	}

	public void register() {

	}
}
