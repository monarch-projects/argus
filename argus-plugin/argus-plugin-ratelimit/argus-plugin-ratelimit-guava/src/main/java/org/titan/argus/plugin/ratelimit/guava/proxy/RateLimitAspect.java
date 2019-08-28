package org.titan.argus.plugin.ratelimit.guava.proxy;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.titan.argus.plugin.ratelimit.guava.cache.CacheData;
import org.titan.argus.plugin.ratelimit.guava.cache.RateLimitDataCache;
import org.titan.argus.plugin.ratelimit.guava.cache.RateLimiterCache;

import java.time.Duration;
import java.util.Date;

/**
 * @Title: RateLimitAspect
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/24
 */
@Aspect
@Order
@EnableAspectJAutoProxy
@Slf4j
@Configuration
public class RateLimitAspect {

    @Pointcut("@annotation(org.titan.argus.plugin.ratelimit.commons.annotations.RateLimit)")
    public void pointcut() {
    }

    @Around("pointcut()")
    @SuppressWarnings("all")
    public Object aspect(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        String className = signature.getDeclaringTypeName();
        String methodName = signature.getMethod().getName();
        CacheData cacheData = RateLimitDataCache.getLimit(className, methodName);
        log.info("className:{}  methodName:{}  limit:{}", className, methodName, cacheData.getLimit());
        RateLimiter limiter = RateLimiterCache.getLimiter(className, methodName, cacheData);
        if (!limiter.tryAcquire(1, Duration.ofMillis(cacheData.getWaitTime())))
            throw new RuntimeException("该请求被限流");
        log.info("currentTime:{}", new Date().getTime());
        return point.proceed();
    }

}
