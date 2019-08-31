package org.titan.argus.server.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: AmqpConfiguration
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/31
 */
@Configuration
public class ArgusAmqpConfiguration {


    @Bean
    public Queue queue() {
        return new Queue("argusQueue", true, false, false);
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
    public RabbitTemplate template(ConnectionFactory factory) {

        RabbitTemplate template = new RabbitTemplate(factory);
        template.setExchange("argusExchange");
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }


    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
}
