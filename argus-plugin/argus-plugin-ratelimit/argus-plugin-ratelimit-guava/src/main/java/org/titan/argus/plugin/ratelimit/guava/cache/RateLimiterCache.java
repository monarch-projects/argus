package org.titan.argus.plugin.ratelimit.guava.cache;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.titan.argus.plugin.ratelimit.commons.annotations.RateLimit;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Title: RateLimiterCache
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/24
 */
@SuppressWarnings("all")
@Slf4j
public class RateLimiterCache {
    private static final Map<String, RateLimiter> MAP = new ConcurrentHashMap<>();

    public static RateLimiter getLimiter(String className, String methodName, CacheData cacheData) {
        String key = className + "_" + methodName + "_" + cacheData.getLimit();
        RateLimiter limiter = MAP.get(key);
        if (Objects.isNull(limiter)) {
            log.info("{} get limiter is null", key);
            limiter = RateLimiter.create(cacheData.getLimit());
            MAP.put(key, limiter);
        }

        return limiter;
    }


    public static RateLimiter updateLimiter(String className, String methodName, CacheData cacheData) {
        String tmp = className + "_" + methodName;
        String key = tmp + "_" + cacheData.getLimit();
        RateLimiter limiter = RateLimiter.create(cacheData.getLimit());
        MAP.put(key, limiter);
        MAP.entrySet().removeIf(e -> e.getKey().startsWith(tmp) && e.getKey().equals(key));
        return limiter;
    }

    public static void initCache(String className, String methodName, RateLimit limit) {
        String key = className + "_" + methodName + "_" + limit.limit();
        MAP.put(key, RateLimiter.create(limit.limit()));
    }

    private static Collection<String> getLocalLimiteStrategy() {
        return MAP.keySet();
    }
}
