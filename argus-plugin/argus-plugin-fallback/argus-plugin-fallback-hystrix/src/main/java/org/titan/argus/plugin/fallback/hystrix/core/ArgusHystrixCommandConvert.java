package org.titan.argus.plugin.fallback.hystrix.core;


import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;


public class ArgusHystrixCommandConvert {

	public static ArgusHystrixProperties convert(HystrixCommandProperties commandProperties, HystrixThreadPoolProperties threadPoolProperties) {
		return ArgusHystrixProperties.builder()
				.circuitBreakerEnabled(commandProperties.circuitBreakerEnabled().get())
				.circuitBreakerErrorThresholdPercentage(commandProperties.circuitBreakerErrorThresholdPercentage().get())
				.circuitBreakerForceClosed(commandProperties.circuitBreakerForceClosed().get())
				.circuitBreakerForceOpen(commandProperties.circuitBreakerForceOpen().get())
				.circuitBreakerRequestVolumeThreshold(commandProperties.circuitBreakerRequestVolumeThreshold().get())
				.circuitBreakerSleepWindowInMilliseconds(commandProperties.circuitBreakerSleepWindowInMilliseconds().get())
				.executionIsolationSemaphoreMaxConcurrentRequests(commandProperties.executionIsolationSemaphoreMaxConcurrentRequests().get())
				.executionIsolationStrategy(commandProperties.executionIsolationStrategy().get().name().equalsIgnoreCase("name") ? "thread" : "semaphore")
				.executionIsolationThreadInterruptOnFutureCancel(commandProperties.executionIsolationThreadInterruptOnFutureCancel().get())
				.executionIsolationThreadInterruptOnTimeout(commandProperties.executionIsolationThreadInterruptOnTimeout().get())
				.executionTimeoutEnabled(commandProperties.executionTimeoutEnabled().get())
				.executionTimeoutInMilliseconds(commandProperties.executionTimeoutInMilliseconds().get())
				.metricsHealthSnapshotIntervalInMilliseconds(commandProperties.metricsHealthSnapshotIntervalInMilliseconds().get())
				.metricsRollingPercentileBucketSize(commandProperties.metricsRollingPercentileBucketSize().get())
				.metricsRollingPercentileEnabled(commandProperties.metricsRollingPercentileEnabled().get())
				.metricsRollingPercentileWindowBuckets(commandProperties.metricsRollingPercentileWindowBuckets().get())
				.metricsRollingPercentileWindowInMilliseconds(commandProperties.metricsRollingPercentileWindowInMilliseconds().get())
				.metricsRollingStatisticalWindowBuckets(commandProperties.metricsRollingStatisticalWindowBuckets().get())
				.metricsRollingStatisticalWindowInMilliseconds(commandProperties.metricsRollingStatisticalWindowInMilliseconds().get())
				.fallbackEnabled(commandProperties.fallbackEnabled().get())
				.fallbackIsolationSemaphoreMaxConcurrentRequests(commandProperties.fallbackIsolationSemaphoreMaxConcurrentRequests().get())
				.requestCacheEnabled(commandProperties.requestCacheEnabled().get())
				.requestLogEnabled(commandProperties.requestLogEnabled().get())
				.corePoolSize(threadPoolProperties.coreSize().get())
				.maximumPoolSize(threadPoolProperties.maximumSize().get())
				.keepAliveTime(threadPoolProperties.keepAliveTimeMinutes().get())
				.maxQueueSize(threadPoolProperties.maxQueueSize().get())
				.queueSizeRejectionThreshold(threadPoolProperties.queueSizeRejectionThreshold().get())
				.allowMaximumSizeToDivergeFromCoreSize(threadPoolProperties.getAllowMaximumSizeToDivergeFromCoreSize().get())
				.threadPoolRollingNumberStatisticalWindowInMilliseconds(threadPoolProperties.metricsRollingStatisticalWindowInMilliseconds().get())
				.threadPoolRollingNumberStatisticalWindowBuckets(threadPoolProperties.metricsRollingStatisticalWindowBuckets().get())
				.build();
	}

	public static HystrixThreadPoolProperties.Setter convertToHystrixThreadPoolProperties(ArgusHystrixProperties properties) {
		HystrixThreadPoolProperties.Setter setter = HystrixThreadPoolProperties.Setter();
		if (properties.getAllowMaximumSizeToDivergeFromCoreSize() != null) {
			setter.withAllowMaximumSizeToDivergeFromCoreSize(properties.getAllowMaximumSizeToDivergeFromCoreSize());
		}
		if (properties.getCorePoolSize() != null) {
			setter.withCoreSize(properties.getCorePoolSize());
		}
		if (properties.getKeepAliveTime() != null) {
			setter.withKeepAliveTimeMinutes(properties.getKeepAliveTime());
		}
		if (properties.getMaximumPoolSize() != null) {
			setter.withMaximumSize(properties.getMaximumPoolSize());
		}
		if (properties.getMaxQueueSize() != null) {
			setter.withMaxQueueSize(properties.getMaxQueueSize());
		}
		if (properties.getMetricsRollingStatisticalWindowBuckets() != null) {
			setter.withMetricsRollingStatisticalWindowBuckets(properties.getMetricsRollingStatisticalWindowBuckets());
		}
		if (properties.getQueueSizeRejectionThreshold() != null) {
			setter.withQueueSizeRejectionThreshold(properties.getQueueSizeRejectionThreshold());
		}
		if (properties.getMetricsHealthSnapshotIntervalInMilliseconds() != null) {
			setter.withMetricsRollingStatisticalWindowInMilliseconds(properties.getMetricsHealthSnapshotIntervalInMilliseconds());
		}
		return setter;
	}

	public static HystrixCommandProperties.Setter convertToHystrixCommandProperties(ArgusHystrixProperties properties) {
		return HystrixCommandProperties.Setter()
				.withCircuitBreakerEnabled(properties.getCircuitBreakerEnabled())
				.withCircuitBreakerErrorThresholdPercentage(properties.getCircuitBreakerErrorThresholdPercentage())
				.withCircuitBreakerForceClosed(properties.getCircuitBreakerForceClosed())
				.withCircuitBreakerForceOpen(properties.getCircuitBreakerForceOpen())
				.withCircuitBreakerRequestVolumeThreshold(properties.getCircuitBreakerRequestVolumeThreshold())
				.withCircuitBreakerSleepWindowInMilliseconds(properties.getCircuitBreakerSleepWindowInMilliseconds())
				.withExecutionIsolationSemaphoreMaxConcurrentRequests(properties.getExecutionIsolationSemaphoreMaxConcurrentRequests())
				.withExecutionIsolationStrategy(properties.getExecutionIsolationStrategy().equals("thread") ?
						HystrixCommandProperties.ExecutionIsolationStrategy.THREAD :
						HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
				.withExecutionIsolationThreadInterruptOnFutureCancel(properties.getExecutionIsolationThreadInterruptOnFutureCancel())
				.withExecutionIsolationThreadInterruptOnTimeout(properties.getExecutionIsolationThreadInterruptOnTimeout())
				.withExecutionTimeoutEnabled(properties.getExecutionTimeoutEnabled())
				.withExecutionTimeoutInMilliseconds(properties.getExecutionTimeoutInMilliseconds())
				.withMetricsHealthSnapshotIntervalInMilliseconds(properties.getMetricsHealthSnapshotIntervalInMilliseconds())
				.withMetricsRollingPercentileBucketSize(properties.getMetricsRollingPercentileBucketSize())
				.withMetricsRollingPercentileEnabled(properties.getMetricsRollingPercentileEnabled())
				.withMetricsRollingPercentileWindowBuckets(properties.getMetricsRollingPercentileWindowBuckets())
				.withMetricsRollingPercentileWindowInMilliseconds(properties.getMetricsRollingPercentileWindowInMilliseconds())
				.withMetricsRollingStatisticalWindowBuckets(properties.getMetricsRollingStatisticalWindowBuckets())
				.withMetricsRollingStatisticalWindowInMilliseconds(properties.getMetricsRollingStatisticalWindowInMilliseconds())
				.withFallbackEnabled(properties.getFallbackEnabled())
				.withFallbackIsolationSemaphoreMaxConcurrentRequests(properties.getFallbackIsolationSemaphoreMaxConcurrentRequests())
				.withRequestCacheEnabled(properties.getRequestCacheEnabled())
				.withRequestLogEnabled(properties.getRequestLogEnabled());
	}
}

