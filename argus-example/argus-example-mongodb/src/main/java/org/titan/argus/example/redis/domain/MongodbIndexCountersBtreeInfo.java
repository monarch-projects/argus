package org.titan.argus.example.redis.domain;

/**
 * @author starboyate
 */
public class MongodbIndexCountersBtreeInfo {
	/**
	 * 访问索引次数
	 */
	private Long accesses;

	/**
	 * 访问索引时，索引在内存中被命中的次数
	 */
	private Long hits;

	/**
	 * 索引计数器被重置的次数
	 */
	private Long resets;

	/**
	 * 索引非命中率
	 */
	private Long missRatio;

}
