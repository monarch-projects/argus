package org.titan.argus.example.redis.domain;

/**
 * @author starboyate
 */
public class MongodbGlobalLockInfo {
	/**
	 * 自实例启动全局锁创建以来到现在多长时间，单位微秒
	 */
	private Long totalTime;

	/**
	 * 自全局锁创建以来锁定总时间，单位微秒
	 */
	private Long lockTime;

	/**
	 * 锁定的时间所占的比例(lockTime/ totalTime)
	 */
	private Float ratio;
	
}
