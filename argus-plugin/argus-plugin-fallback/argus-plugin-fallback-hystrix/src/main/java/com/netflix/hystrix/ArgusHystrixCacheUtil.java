package com.netflix.hystrix;

import com.netflix.hystrix.strategy.properties.HystrixPropertiesFactory;

/**
 * @author starboyate
 */
public class ArgusHystrixCacheUtil {
	public static void threadPoolsCacheRemove(String commandKey) {
		HystrixThreadPool.Factory.threadPools.remove(commandKey);
		HystrixCommandMetrics.reset();
	}

	public static void circuitBreakerCacheReset() {
		HystrixCircuitBreaker.Factory.reset();
	}

	public static void propertiesCacheReset() {
		HystrixPropertiesFactory.reset();
	}
}
