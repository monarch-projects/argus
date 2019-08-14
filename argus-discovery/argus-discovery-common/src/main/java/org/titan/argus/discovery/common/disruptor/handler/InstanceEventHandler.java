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
import org.titan.argus.tools.alarm.config.AlarmHolder;
import org.titan.argus.tools.alarm.entities.MailAlarm;
import org.titan.argus.tools.alarm.enums.AlarmTypeEnum;
import org.titan.argus.tools.alarm.handler.EmailSender;
import org.titan.argus.tools.alarm.handler.SenderHolder;

/**
 * @author starboyate
 */
public class InstanceEventHandler implements EventHandler<DisruptorMessage> {
	private Logger logger = LoggerFactory.getLogger(InstanceEventHandler.class);

	private final JavaMailSender sender;


	private final AlarmHolder holder;

	public InstanceEventHandler(JavaMailSender sender, AlarmHolder holder) {
		this.sender = sender;
		this.holder = holder;
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
			register(info);
		}
	}

	private void offline(ArgusDiscoveryEventInfo info) {

		this.holder.getAlarms().forEach(item -> {
			MailAlarm alarm = (MailAlarm) item.getAlarm();
			if (alarm.getAlarmType().equalsIgnoreCase(AlarmTypeEnum.EMAIL.name())) {
				SenderHolder senderHolder = new SenderHolder(new EmailSender(item.getSenderHelper().getEmailSender()));
				alarm.setBody(info);
				senderHolder.send(item.getAlarm());
			}
		});


	}

	/**
	 * TODO
	 * @param info
	 */
	private void register(ArgusDiscoveryEventInfo info) {

	}
}
