package org.titan.argus.example.redis.domain;

/**
 * @author starboyate
 */
public class MongodbActiveClientsInfo {
	/**
	 * 连接到当前实例处于活动状态的客户端数量
	 */
	private Long total;

	/**
	 * 处于活动状态的客户端中有多少是在执行read操作
	 */
	private Long readers;

	/**
	 * 处于活动状态的客户端中有多少是在执行write操作
	 */
	private Long writers;
}
