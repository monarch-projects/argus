package org.titan.argus.example.redis.domain;

/**
 * @author starboyate
 */
public class MongodbGlobalLockCurrentQueueInfo {
	/**
	 * 当前等待全局锁的数量
	 */
	private Long total;

	/**
	 * 当前等待读锁的数量
	 */
	private Long readers;

	/**
	 * 当前等待写锁的数量
	 */
	private Long writers;
}
