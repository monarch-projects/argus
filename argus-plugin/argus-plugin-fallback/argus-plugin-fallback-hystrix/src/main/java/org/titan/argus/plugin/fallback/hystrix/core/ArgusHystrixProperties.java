package org.titan.argus.plugin.fallback.hystrix.core;

import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.strategy.properties.HystrixProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author starboyate
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArgusHystrixProperties {

	private Integer circuitBreakerRequestVolumeThreshold; // number of requests that must be made within a statisticalWindow before open/close decisions are made using stats
	private Integer circuitBreakerSleepWindowInMilliseconds; // milliseconds after tripping circuit before allowing retry
	private Boolean circuitBreakerEnabled; // Whether circuit breaker should be enabled.
	private Integer circuitBreakerErrorThresholdPercentage; // % of 'marks' that must be failed to trip the circuit
	private Boolean circuitBreakerForceOpen; // a property to allow forcing the circuit open (stopping all requests)
	private Boolean circuitBreakerForceClosed; // a property to allow ignoring errors and therefore never trip 'open' (ie. allow all traffic through)
	private String executionIsolationStrategy; // Whether a command should be executed in a separate thread or not.
	private Integer executionTimeoutInMilliseconds; // Timeout value in milliseconds for a command
	private Boolean executionTimeoutEnabled; //Whether timeout should be triggered
	private String executionIsolationThreadPoolKeyOverride; // What thread-pool this command should run in (if running on a separate thread).
	private Integer executionIsolationSemaphoreMaxConcurrentRequests; // Number of permits for execution semaphore
	private Integer fallbackIsolationSemaphoreMaxConcurrentRequests; // Number of permits for fallback semaphore
	private Boolean fallbackEnabled; // Whether fallback should be attempted.
	private Boolean executionIsolationThreadInterruptOnTimeout; // Whether an underlying Future/Thread (when runInSeparateThread == true) should be interrupted after a timeout
	private Boolean executionIsolationThreadInterruptOnFutureCancel; // Whether canceling an underlying Future/Thread (when runInSeparateThread == true) should interrupt the execution thread
	private Integer metricsRollingStatisticalWindowInMilliseconds; // milliseconds back that will be tracked
	private Integer metricsRollingStatisticalWindowBuckets; // number of buckets in the statisticalWindow
	private Boolean metricsRollingPercentileEnabled; // Whether monitoring should be enabled (SLA and Tracers).
	private Integer metricsRollingPercentileWindowInMilliseconds; // number of milliseconds that will be tracked in RollingPercentile
	private Integer metricsRollingPercentileWindowBuckets; // number of buckets percentileWindow will be divided into
	private Integer metricsRollingPercentileBucketSize; // how many values will be stored in each percentileWindowBucket
	private Integer metricsHealthSnapshotIntervalInMilliseconds; // time between health snapshots
	private Boolean requestLogEnabled; // whether command request logging is enabled.
	private Boolean requestCacheEnabled; // Whether request caching is enabled.
	private Integer corePoolSize;
	private Integer maximumPoolSize;
	private Integer keepAliveTime;
	private Integer maxQueueSize;
	private Integer queueSizeRejectionThreshold;
	private Boolean allowMaximumSizeToDivergeFromCoreSize;
	private Integer threadPoolRollingNumberStatisticalWindowInMilliseconds;
	private Integer threadPoolRollingNumberStatisticalWindowBuckets;

}
