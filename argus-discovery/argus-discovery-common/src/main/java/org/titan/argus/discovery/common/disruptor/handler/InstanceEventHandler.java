package org.titan.argus.discovery.common.disruptor.handler;

import com.alibaba.fastjson.JSON;
import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.javamail.JavaMailSender;
import org.titan.argus.discovery.common.disruptor.event.ArgusInstanceOfflineEvent;
import org.titan.argus.discovery.common.disruptor.event.ArgusInstanceRegisteredEvent;
import org.titan.argus.discovery.common.disruptor.event.InstanceEvent;
import org.titan.argus.discovery.common.disruptor.message.DisruptorMessage;
import org.titan.argus.discovery.common.entities.ArgusDiscoveryEventInfo;
import org.titan.argus.discovery.common.event.AlarmLogEvent;
import org.titan.argus.model.entities.Alarm;
import org.titan.argus.model.entities.AlarmLog;
import org.titan.argus.tools.alarm.config.AlarmHolder;
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

	private final ApplicationEventPublisher publisher;

	public InstanceEventHandler(JavaMailSender sender, AlarmHolder holder, ApplicationEventPublisher publisher) {
		this.sender = sender;
		this.holder = holder;
		this.publisher = publisher;
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
			logger.info("notify offline, appName: {}, eventType: {}, updateTime: {}, instanceId: {}", event.getAppName(), event.getEventType(), event.getUpdateTime(), event.getId());
		} else if (event instanceof ArgusInstanceRegisteredEvent) {
			logger.info("notify registered, appName: {}, eventType: {}, updateTime: {}, instanceId: {}", event.getAppName(), event.getEventType(), event.getUpdateTime(), event.getId());
		}
		doInvoker(info);
	}


	private void doInvoker(ArgusDiscoveryEventInfo info) {
		this.holder.getAlarms().forEach(item -> {
			Alarm alarm = item.getAlarm();
			AlarmLog convert = alarm.convert();
			if (alarm.getAlarmType().equalsIgnoreCase(AlarmTypeEnum.EMAIL.name())) {
				SenderHolder senderHolder = new SenderHolder(new EmailSender(item.getSenderHelper().getEmailSender()));
				convert.setBody(JSON.toJSONString(info));
				convert.setSubject(info.getEvent());
				senderHolder.send(convert);
			} else if (alarm.getAlarmType().equalsIgnoreCase(AlarmTypeEnum.SMS.name())) {
				// TODO
			}
			this.publisher.publishEvent(new AlarmLogEvent( this, convert));
		});
	}
}
