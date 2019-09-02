package org.titan.argus.server.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.titan.argus.model.dto.RateLimitConfigDTO;
import org.titan.argus.model.message.EventBusMessage;
import org.titan.argus.model.message.RateLimiterAppStartMessage;
import org.titan.argus.model.message.UpdateRateLimitMessage;
import org.titan.argus.plugin.ratelimit.commons.util.EventBusUtil;
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
@RabbitListener(queues = {"argusQueue"})
public class RateLimiterServerBusListener {

    @Autowired
    private EventBusUtil busUtil;
    @Autowired
    private RateLimitInfoService infoService;
    private ReentrantLock lock = new ReentrantLock();


    /**
     * 在限流app启动之时，接收客户端发来的限流策略，持久化到db，然后将db中的限流策略推到客户端
     *
     * @param message
     */
    @RabbitHandler(isDefault = true)
    public void process(EventBusMessage message) {
        log.info("收到event bus消息:{}", message);
        switch (message.getType()) {
            case EventBusMessage.RATE_LIMIT_ONLINE:
                lock.lock();
                try {
                    RateLimiterAppStartMessage startMessage = message.convert(RateLimiterAppStartMessage.class);
                    this.infoService.processClientData(startMessage.getData());
                    Collection<RateLimitConfigDTO> configs = this.infoService.getConfigByAppName(startMessage.getAppName());
                    this.busUtil.sendMessage(EventBusMessage.RATE_LIMIT_CONFIGS, new UpdateRateLimitMessage().setConfigs(configs));
                    log.info("成功向客户端发送限流配置信息:{}", configs);
                } finally {
                    lock.unlock();
                }
                break;
            default:
                return;
        }
    }
}
