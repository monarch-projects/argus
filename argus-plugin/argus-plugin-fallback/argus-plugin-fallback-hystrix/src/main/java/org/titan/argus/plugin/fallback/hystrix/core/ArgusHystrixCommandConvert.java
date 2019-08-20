package org.titan.argus.plugin.fallback.hystrix.core;


import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

/**
 * @author starboyate
 */
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
		HystrixCommandProperties.Setter setter = HystrixCommandProperties.Setter();
		if (properties.getCircuitBreakerEnabled() != null) {
			setter.withCircuitBreakerEnabled(properties.getCircuitBreakerEnabled());
		}
		if (properties.getCircuitBreakerErrorThresholdPercentage() != null) {
			setter.withCircuitBreakerErrorThresholdPercentage(properties.getCircuitBreakerErrorThresholdPercentage());
		}
		if (properties.getCircuitBreakerForceClosed() != null) {
			setter.withCircuitBreakerForceClosed(properties.getCircuitBreakerForceClosed());
		}
		if (properties.getCircuitBreakerForceOpen() != null) {
			setter.withCircuitBreakerForceOpen(properties.getCircuitBreakerForceOpen());
		}
		if (properties.getCircuitBreakerRequestVolumeThreshold() != null) {
			setter.withCircuitBreakerRequestVolumeThreshold(properties.getCircuitBreakerRequestVolumeThreshold());
		}
		if (properties.getCircuitBreakerSleepWindowInMilliseconds() != null) {
			setter.withCircuitBreakerSleepWindowInMilliseconds(properties.getCircuitBreakerSleepWindowInMilliseconds());
		}
		if (properties.getExecutionIsolationSemaphoreMaxConcurrentRequests() != null) {
			setter.withExecutionIsolationSemaphoreMaxConcurrentRequests(properties.getExecutionIsolationSemaphoreMaxConcurrentRequests());
		}
		if (properties.getExecutionIsolationStrategy() != null) {
			setter.withExecutionIsolationStrategy(properties.getExecutionIsolationStrategy().equals("thread") ?
					HystrixCommandProperties.ExecutionIsolationStrategy.THREAD :
					HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE);
		}
		if (properties.getExecutionIsolationThreadInterruptOnFutureCancel() != null) {
			setter.withExecutionIsolationThreadInterruptOnFutureCancel(properties.getExecutionIsolationThreadInterruptOnFutureCancel());
		}
		if (properties.getExecutionIsolationThreadInterruptOnTimeout() != null) {
			setter.withExecutionIsolationThreadInterruptOnTimeout(properties.getExecutionIsolationThreadInterruptOnTimeout());
		}
		if (properties.getExecutionTimeoutEnabled() != null) {
			setter.withExecutionTimeoutEnabled(properties.getExecutionTimeoutEnabled());
		}
		if (properties.getExecutionTimeoutInMilliseconds() != null) {
			setter.withExecutionTimeoutInMilliseconds(properties.getExecutionTimeoutInMilliseconds());
		}
		if (properties.getMetricsHealthSnapshotIntervalInMilliseconds() != null) {
			setter.withMetricsHealthSnapshotIntervalInMilliseconds(properties.getMetricsHealthSnapshotIntervalInMilliseconds());
		}
		if (properties.getMetricsRollingPercentileBucketSize() != null) {
			setter.withMetricsRollingPercentileBucketSize(properties.getMetricsRollingPercentileBucketSize());
		}
		if (properties.getMetricsRollingPercentileEnabled() != null) {
			setter.withMetricsRollingPercentileEnabled(properties.getMetricsRollingPercentileEnabled());
		}
		if (properties.getMetricsRollingPercentileWindowBuckets() != null) {
			setter.withMetricsRollingPercentileWindowBuckets(properties.getMetricsRollingPercentileWindowBuckets());
		}
		if (properties.getMetricsRollingPercentileWindowInMilliseconds() != null) {
			setter.withMetricsRollingPercentileWindowInMilliseconds(properties.getMetricsRollingPercentileWindowInMilliseconds());
		}
		if (properties.getMetricsRollingStatisticalWindowBuckets() != null) {
			setter.withMetricsRollingStatisticalWindowBuckets(properties.getMetricsRollingStatisticalWindowBuckets());
		}
		if (properties.getMetricsRollingStatisticalWindowInMilliseconds() != null) {
			setter.withMetricsRollingStatisticalWindowInMilliseconds(properties.getMetricsRollingStatisticalWindowInMilliseconds());
		}
		if (properties.getFallbackEnabled() != null) {
			setter.withFallbackEnabled(properties.getFallbackEnabled());
		}
		if (properties.getFallbackIsolationSemaphoreMaxConcurrentRequests() != null) {
			setter.withFallbackIsolationSemaphoreMaxConcurrentRequests(properties.getFallbackIsolationSemaphoreMaxConcurrentRequests());
		}
		if (properties.getRequestCacheEnabled() != null) {
			setter.withRequestCacheEnabled(properties.getRequestCacheEnabled());
		}
		if (properties.getRequestLogEnabled() != null) {
			setter.withRequestLogEnabled(properties.getRequestLogEnabled());
		}

		return setter;
	}

	public static ArgusHystrixProperties copyProperties(ArgusHystrixProperties source, ArgusHystrixProperties target) {
		if (source != null && target != null) {
			if (source.getCircuitBreakerEnabled() != null) {
				target.setCircuitBreakerEnabled(source.getCircuitBreakerEnabled());
			}
			if (source.getCircuitBreakerErrorThresholdPercentage() != null) {
				target.setCircuitBreakerErrorThresholdPercentage(source.getCircuitBreakerErrorThresholdPercentage());
			}
			if (source.getCircuitBreakerForceClosed() != null) {
				target.setCircuitBreakerForceClosed(source.getCircuitBreakerForceClosed());
			}
			if (source.getCircuitBreakerForceOpen() != null) {
				target.setCircuitBreakerForceOpen(source.getCircuitBreakerForceOpen());
			}
			if (source.getCircuitBreakerRequestVolumeThreshold() != null) {
				target.setCircuitBreakerRequestVolumeThreshold(source.getCircuitBreakerRequestVolumeThreshold());
			}
			if (source.getCircuitBreakerSleepWindowInMilliseconds() != null) {
				target.setCircuitBreakerSleepWindowInMilliseconds(source.getCircuitBreakerSleepWindowInMilliseconds());
			}
			if (source.getExecutionIsolationSemaphoreMaxConcurrentRequests() != null) {
				target.setExecutionIsolationSemaphoreMaxConcurrentRequests(source.getExecutionIsolationSemaphoreMaxConcurrentRequests());
			}
			if (source.getExecutionIsolationStrategy() != null) {
				target.setExecutionIsolationStrategy(source.getExecutionIsolationStrategy());
			}
			if (source.getExecutionIsolationThreadInterruptOnFutureCancel() != null) {
				target.setExecutionIsolationThreadInterruptOnFutureCancel(source.getExecutionIsolationThreadInterruptOnFutureCancel());
			}
			if (source.getExecutionIsolationThreadInterruptOnTimeout() != null) {
				target.setExecutionIsolationThreadInterruptOnTimeout(source.getExecutionIsolationThreadInterruptOnTimeout());
			}
			if (source.getExecutionTimeoutEnabled() != null) {
				target.setExecutionTimeoutEnabled(source.getExecutionTimeoutEnabled());
			}
			if (source.getExecutionTimeoutInMilliseconds() != null) {
				target.setExecutionTimeoutInMilliseconds(source.getExecutionTimeoutInMilliseconds());
			}
			if (source.getMetricsHealthSnapshotIntervalInMilliseconds() != null) {
				target.setMetricsHealthSnapshotIntervalInMilliseconds(source.getMetricsHealthSnapshotIntervalInMilliseconds());
			}
			if (source.getMetricsRollingPercentileBucketSize() != null) {
				target.setMetricsRollingPercentileBucketSize(source.getMetricsRollingPercentileBucketSize());
			}
			if (source.getMetricsRollingPercentileEnabled() != null) {
				target.setMetricsRollingPercentileEnabled(source.getMetricsRollingPercentileEnabled());
			}
			if (source.getMetricsRollingPercentileWindowBuckets() != null) {
				target.setMetricsRollingPercentileWindowBuckets(source.getMetricsRollingPercentileWindowBuckets());
			}
			if (source.getMetricsRollingPercentileWindowInMilliseconds() != null) {
				target.setMetricsRollingPercentileWindowInMilliseconds(source.getMetricsRollingPercentileWindowInMilliseconds());
			}
			if (source.getMetricsRollingStatisticalWindowBuckets() != null) {
				target.setMetricsRollingStatisticalWindowBuckets(source.getMetricsRollingStatisticalWindowBuckets());
			}
			if (source.getMetricsRollingStatisticalWindowInMilliseconds() != null) {
				target.setMetricsRollingStatisticalWindowInMilliseconds(source.getMetricsRollingStatisticalWindowInMilliseconds());
			}
			if (source.getFallbackEnabled() != null) {
				target.setFallbackEnabled(source.getFallbackEnabled());
			}
			if (source.getFallbackIsolationSemaphoreMaxConcurrentRequests() != null) {
				target.setFallbackIsolationSemaphoreMaxConcurrentRequests(source.getFallbackIsolationSemaphoreMaxConcurrentRequests());
			}
			if (source.getRequestCacheEnabled() != null) {
				target.setRequestCacheEnabled(source.getRequestCacheEnabled());
			}
			if (source.getRequestLogEnabled() != null) {
				target.setRequestLogEnabled(source.getRequestLogEnabled());
			}
			if (source.getAllowMaximumSizeToDivergeFromCoreSize() != null) {
				target.setAllowMaximumSizeToDivergeFromCoreSize(source.getAllowMaximumSizeToDivergeFromCoreSize());
			}
			if (source.getCorePoolSize() != null) {
				target.setCorePoolSize(source.getCorePoolSize());
			}
			if (source.getKeepAliveTime() != null) {
				target.setKeepAliveTime(source.getKeepAliveTime());
			}
			if (source.getMaximumPoolSize() != null) {
				target.setMaximumPoolSize(source.getMaximumPoolSize());
			}
			if (source.getMaxQueueSize() != null) {
				target.setMaxQueueSize(source.getMaxQueueSize());
			}
			if (source.getMetricsRollingStatisticalWindowBuckets() != null) {
				target.setMetricsRollingStatisticalWindowBuckets(source.getMetricsRollingStatisticalWindowBuckets());
			}
			if (source.getQueueSizeRejectionThreshold() != null) {
				target.setQueueSizeRejectionThreshold(source.getQueueSizeRejectionThreshold());
			}
			if (source.getMetricsHealthSnapshotIntervalInMilliseconds() != null) {
				target.setMetricsRollingStatisticalWindowInMilliseconds(source.getMetricsHealthSnapshotIntervalInMilliseconds());
			}
		}
		return target;
	}
}

