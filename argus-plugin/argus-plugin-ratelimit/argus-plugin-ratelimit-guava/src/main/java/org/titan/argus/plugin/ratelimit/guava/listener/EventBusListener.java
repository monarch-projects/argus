package org.titan.argus.plugin.ratelimit.guava.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.titan.argus.model.message.BaseMessage;
import org.titan.argus.util.JsonUtil;

/**
 * @Title: EventBusListener
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/28
 */
@Configuration
@Slf4j
@RabbitListener(bindings = {@QueueBinding(value = @org.springframework.amqp.rabbit.annotation.Queue,
        key = {"argus"}, exchange = @Exchange(value = "argus"))})
public class EventBusListener {

    @Value("${spring.application.name}")
    private String appName;


    @Autowired
    private AmqpTemplate template;

    @RabbitHandler
    public void process(String message) {
        BaseMessage baseMessage = JsonUtil.decode(message, BaseMessage.class);
        if (baseMessage.getType().equals(BaseMessage.RATE_LIMIT))

            log.info("message:{}", message);
    }
}
