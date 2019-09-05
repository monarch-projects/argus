package org.titan.argus.example.redis.domain;

public class MongodbConnections {
	/**
	 * 当前连接到本机处于活动状态的连接数
	 */
	private Long current;

	/**
	 * 剩余多少可供连接
	 */
	private Long available;

	/**
	 * 总共创建的连接数
	 */
	private Long totalCreated;

	/**
	 * 当前存活的连接数
	 */
	private Long active;
}
