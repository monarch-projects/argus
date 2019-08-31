package org.titan.argus.plugin.ratelimit.guava.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.titan.argus.model.message.EventBusMessage;
import org.titan.argus.plugin.ratelimit.guava.listener.RateLimiterBusListener;

import java.util.UUID;


/**
 * @Title: AmqpConfiguration
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/31
 */
@Configuration
@Slf4j
@SuppressWarnings("all")
public class ArgusAmqpConfiguration {

    public static final String QUEUE_NAME = "argusQueue_" + UUID.randomUUID().toString().replaceAll("_", "");

    private static final Jackson2JsonMessageConverter CONVERTER = new Jackson2JsonMessageConverter();

    @Bean
    public Queue queue() {
        log.info("准备创建队列，队列名:{}", QUEUE_NAME);
        return new Queue(QUEUE_NAME, true, false, true);
    }

    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange("argusExchange", true, false);
    }

    @Bean
    public Binding binding(Queue queue, FanoutExchange exchange) {

        return BindingBuilder.bind(queue).to(exchange);
    }


    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory factory, RateLimiterBusListener listener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(factory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
            try {
                EventBusMessage busMessage = (EventBusMessage) CONVERTER.fromMessage(message);
                listener.process(busMessage);
            } catch (Exception e) {
                log.error("处理消息队列任务失败！", e);
            }
        });
        return container;
    }

    @Bean
    public RabbitTemplate template(ConnectionFactory factory) {
        RabbitTemplate template = new RabbitTemplate(factory);
        template.setExchange("argusExchange");
        template.setMessageConverter(CONVERTER);
        return template;
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return CONVERTER;
    }
}
