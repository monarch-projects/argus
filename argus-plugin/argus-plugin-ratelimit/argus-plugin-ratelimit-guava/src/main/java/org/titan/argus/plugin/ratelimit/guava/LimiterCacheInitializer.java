package org.titan.argus.plugin.ratelimit.guava;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.lang.Nullable;
import org.titan.argus.plugin.ratelimit.commons.annotations.RateLimit;
import org.titan.argus.plugin.ratelimit.guava.cache.RateLimitDataCache;
import org.titan.argus.plugin.ratelimit.guava.cache.RateLimiterCache;

import java.lang.reflect.Method;
import java.util.Arrays;


/**
 * @Title: LimiterCacheInitializer
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/24
 */
@Order
@Configuration
@Slf4j
public class LimiterCacheInitializer implements BeanPostProcessor {
    @Override
    @Nullable
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        this.initCache(bean);
        return bean;
    }


    private void initCache(Object bean) {
        Class<?> clz = bean.getClass().getSuperclass();
        if (clz.equals(Object.class))
            return;
        String className = clz.getName();
        Method[] methods = clz.getDeclaredMethods();
        Arrays.stream(methods).forEach(method -> {
            if (method.isAnnotationPresent(RateLimit.class)) {
                String methodName = method.getName();
                RateLimit limit = method.getDeclaredAnnotation(RateLimit.class);
                log.info("className:{},methodName:{},limit:{}", className, method, limit.limit());
                RateLimitDataCache.addDataCache(className, methodName, limit);
                RateLimiterCache.initCache(className, methodName, limit);
            }
        });
    }
}
