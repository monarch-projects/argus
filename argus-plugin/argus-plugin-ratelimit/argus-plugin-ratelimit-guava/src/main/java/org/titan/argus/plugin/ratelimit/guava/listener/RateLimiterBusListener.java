package org.titan.argus.plugin.ratelimit.guava.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.titan.argus.model.dto.RateLimitConfigDTO;
import org.titan.argus.model.message.BaseMessage;
import org.titan.argus.model.message.RateLimiterAppStartMessage;
import org.titan.argus.model.message.UpdateRateLimitMessage;
import org.titan.argus.model.request.RateLimitClientDataRequest;
import org.titan.argus.plugin.ratelimit.guava.cache.CacheData;
import org.titan.argus.plugin.ratelimit.guava.cache.RateLimitDataCache;
import org.titan.argus.plugin.ratelimit.guava.cache.RateLimiterCache;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Title: EventBusListener
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/28
 */
@Configuration
@Slf4j
@RabbitListener(bindings = {@QueueBinding(value = @org.springframework.amqp.rabbit.annotation.Queue,
        key = {"argus-rate-limit"}, exchange = @Exchange(value = "argus-rate-limit"))})
public class RateLimiterBusListener implements InitializingBean {

    @Value("${spring.application.name}")
    private String appName;


    @Autowired
    private AmqpTemplate template;

    @RabbitHandler
    public void process(BaseMessage message) {

        log.info("收到限流消息总线通知：{}", message);

        switch (message.getType()) {
            case BaseMessage.RATE_LIMIT_UPDATE:
                this.doUpdateRateLimit(message.convert());
            default:
            case BaseMessage.RATE_LIMIT_CONFIGS:
                this.doUpdateRateLimit(message.convert());
        }
    }


    private void doUpdateRateLimit(UpdateRateLimitMessage message) {
        log.info("开始更新本地限流策略");
        Collection<RateLimitConfigDTO> dtos = message.getConfigs();
        if (CollectionUtils.isEmpty(dtos))
            return;
        log.info("going to update cache data and limiter,configs:{}", dtos);
        dtos.forEach(dto -> {
            CacheData data = RateLimitDataCache.updateDataCache(dto.getClzName(), dto.getMethodName(), dto.getLimit(), dto.getWaitTime());
            RateLimiterCache.updateLimiter(dto.getClzName(), dto.getMethodName(), data);
        });

        log.info("更新本地限流策略完毕！");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("将要向服务端推送本地限流策略");
        Set<Map.Entry<String, CacheData>> set = RateLimitDataCache.getCache();

        Collection<RateLimitClientDataRequest> requests = set.stream().map(e -> {
            String[] strings = e.getKey().split("_");
            return new RateLimitClientDataRequest().setClzName(strings[0]).setMethodName(strings[1]).setAppName(this.appName)
                    .setLimit(e.getValue().getLimit()).setWaitTime(e.getValue().getWaitTime());
        }).collect(Collectors.toList());

        log.info("本地限流策略：{}", requests);

        if (requests.isEmpty()) {
            this.template.convertAndSend(BaseMessage.RATE_LIMIT_MESSAGE_KEY, new RateLimiterAppStartMessage().setAppName(this.appName)
                    .setData(Collections.emptyList()));
            log.info("推送本地限流策略完毕！");
            return;
        }

        this.template.convertAndSend(BaseMessage.RATE_LIMIT_MESSAGE_KEY, new RateLimiterAppStartMessage().setData(requests)
                .setAppName(this.appName));

        log.info("推送本地限流策略完毕！");
    }
}
