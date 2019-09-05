package org.titan.argus.example.redis.domain;

/**
 * @author starboyate
 */
public class MongodbCursorsInfo {
	/**
	 * 当前游标数量
	 */
	private Integer totalOpen;

	/**
	 * 从实例启动到现在游标超时的总数量
	 */
	private Integer timeOut;
}
