package org.titan.argus.plugin.ratelimit.commons.util;

import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.titan.argus.model.message.ArgusMessage;
import org.titan.argus.model.message.EventBusMessage;

/**
 * @Title: EventBusUtil
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/31
 */
@Configuration
public class EventBusUtil {

    @Autowired
    private RabbitTemplate template;
    @Autowired
    private Jackson2JsonMessageConverter converter;

    private static final MessageProperties properties = new MessageProperties();

    static {
        properties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
    }

    public void sendMessage(int type, ArgusMessage message) {

        EventBusMessage busMessage = EventBusMessage.getInstance(type).setData(message.json());

        this.template.send(converter.toMessage(busMessage, properties));
    }


    public void sendMessage(int type, String message) {
        EventBusMessage busMessage = EventBusMessage.getInstance(type).setData(message);

        this.template.send(converter.toMessage(busMessage, properties));

    }

}
