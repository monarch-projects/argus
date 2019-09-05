package org.titan.argus.example.redis.domain;

/**
 * @author starboyate
 */
public class MongodbBackGroundFlushingInfo {
	/**
	 * 实例刷新数据到磁盘的数次
	 */
	private Long flushes;

	/**
	 * 刷新到磁盘总共花费的时间,单位毫秒
	 */
	private Long totalMs;

	/**
	 * 平均每次刷新执行时间
	 */
	private Float averageMs;

	/**
	 * 最后一次刷新执行时间
	 */
	private Long lastMs;

	/**
	 * 最后一次刷新完成的时间点
	 */
	private Long last_finished;
}
