package org.titan.argus.example.redis.domain;

/**
 * @author starboyate
 */
public class MongodbOpcountersInfo {
	/**
	 * 自实例启动以来，执行insert次数
	 */
	private Integer insert;

	/**
	 * 自实例启动以来，执行query次数
	 */
	private Integer query;

	/**
	 * 自实例启动以来，执行update次数
	 */
	private Integer update;

	/**
	 * 自实例启动以来，执行delete次数
	 */
	private Integer delete;

	/**
	 * 自实例启动以来，在游标执行getMore次数
	 */
	private Integer getMore;

	/**
	 * 自实例启动以来，执行其他操作的次数
	 */
	private Integer command;
}
