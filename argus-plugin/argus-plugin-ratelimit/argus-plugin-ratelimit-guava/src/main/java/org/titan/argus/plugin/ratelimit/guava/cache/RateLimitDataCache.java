package org.titan.argus.plugin.ratelimit.guava.cache;

import lombok.extern.slf4j.Slf4j;
import org.titan.argus.plugin.ratelimit.commons.annotations.RateLimit;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Title: RateLimitCache
 * @Description:
 * @Author: daozhang
 * @date: 2019/8/24
 */
@Slf4j
public class RateLimitDataCache {

    private static final Map<String, CacheData> MAP = new ConcurrentHashMap<>();
    private static final CacheData DEFAULT_CACHE_DATA = new CacheData(Integer.MAX_VALUE, 500);

    public static CacheData getLimit(String classname, String methodName) {
        return Optional.ofNullable(MAP.get(classname + "_" + methodName)).orElse(DEFAULT_CACHE_DATA);
    }

    public static void addDataCache(String clzName, String methodName, RateLimit limit) {
        log.info("add data cache,className:{},methodName:{},limit:{}", clzName, methodName, limit.limit());
        MAP.put(clzName + "_" + methodName, new CacheData(limit.limit(), limit.waitTime()));
        MAP.forEach((k, v) -> {
            log.info("data cache -> {}:{}", k, v.toString());
        });
    }
}