package org.titan.argus.server.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.titan.argus.model.dto.RateLimitConfigDTO;
import org.titan.argus.model.message.BaseMessage;
import org.titan.argus.model.message.RateLimiterAppStartMessage;
import org.titan.argus.model.message.UpdateRateLimitMessage;
import org.titan.argus.service.RateLimitInfoService;

import java.util.Collection;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Title: RateLimiterServerBusListener
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/30
 */
@Configuration
@Slf4j
@RabbitListener(bindings = {@QueueBinding(value = @org.springframework.amqp.rabbit.annotation.Queue,
        key = {"argus-rate-limit"}, exchange = @Exchange(value = "argus-rate-limit"))})
public class RateLimiterServerBusListener {

    @Autowired
    private AmqpTemplate template;
    @Autowired
    private RateLimitInfoService infoService;
    private ReentrantLock lock = new ReentrantLock();

    /**
     * 在限流app启动之时，接收客户端发来的限流策略，持久化到db，然后将db中的限流策略推到客户端
     *
     * @param message
     */
    @RabbitHandler
    public void process(BaseMessage message) {
        switch (message.getType()) {
            case BaseMessage.RATE_LIMIT_ONLINE:
                lock.lock();
                try {
                    RateLimiterAppStartMessage startMessage = message.convert();
                    this.infoService.processClientData(startMessage.getData());
                    Collection<RateLimitConfigDTO> configs = this.infoService.getConfigByAppName(startMessage.getAppName());
                    this.template.convertAndSend(BaseMessage.RATE_LIMIT_MESSAGE_KEY, new UpdateRateLimitMessage().setConfigs(configs).setType(BaseMessage.RATE_LIMIT_CONFIGS));
                } finally {
                    lock.unlock();
                }
                break;
            default:
                return;
        }
    }
}
